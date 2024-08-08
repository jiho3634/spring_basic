package com.beyond.basic.controller;

import com.beyond.basic.domain.Hello;
import com.beyond.basic.domain.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


//해당 클래스가 컨트롤러( 사용자의 요청을 처리하고 응답하는 편의기능) 임을 명시
@Controller

//@RestController : Controller + 각메서드마다 @ResponseBody

// 클래스 차원에 url 매핑시에 RequestMapping 사용
@RequestMapping("/hello")
//  http://localhost:8080/hello

public class HelloController {

    // ##### case1. 사용자가 서버에게 화면 요청 (get 요청)
    // ##### case2. @ResponseBody 가 붙을경우
    // 단순 String 데이터 return (get 요청)

    // GetMapping 을 통해 get 요청을 처리하고 url 패턴을 명시
    @GetMapping("/")
    //  http://localhost:8080/hello/

    // 디폴트 : return 값이 String 이면
    // templates 디렉터리 밑에 helloWorld.html 을 찾아서 리턴.
    //  메서드에 적용하면 메서드의 반환 값이 JSON 형식으로 변환
    //  스프링은 기본적으로 Jackson 라이브러리를 사용하여 Java 객체를 JSON 으로 변환
    @ResponseBody

    public String helloWorld() {
        //return "hello world" String 을 화면에 출력
        return "hello world";
    }

    // ##### case3. 사용자가 json 데이터 요청 (get 요청)

    //data 를 리턴하되, json 형식으로 return
    // method 명은 helloJson, url 패턴은 /hello/json
    // data -> 객체 -> 직렬화 -> 리턴
    // ObjectMapper : 수동 직렬화 도구
    @GetMapping("/json")
    //  http://localhost:8080/hello/json

    // 메소드 차원에서도 RequestMapping 사용 가능
    @RequestMapping(value = "/json", method = RequestMethod.GET)
    @ResponseBody
    public Hello helloJson() throws JsonProcessingException {
        return new Hello.HelloBuilder().name("hongildong").email("hongildong@naver.com").build();
    }


    // ##### case4. 사용자가 json 데이터를 요청하되,
    // parameter 형식으로 특정 객체 요청.
    @GetMapping("/param1")
    @ResponseBody

    //  http://localhost:8080/hello/param1?name=hongildong1
    public Hello param1(@RequestParam(value = "name")String inputName) {
        return new Hello.HelloBuilder().name(inputName).email("hongildong@naver.com").build();
    }

    @GetMapping("/param2")
    @ResponseBody
    //  http://localhost:8080/hello/param2?name=hongildong2&email=hongildong2@naver.com
    public Hello param12(@RequestParam(value = "name")String inputName, @RequestParam(value = "email")String inputEmail) {
        return new Hello.HelloBuilder().name(inputName).email(inputEmail).build();
    }


    // ##### case5 : parameter 형식으로 요청하되, 서버에서 데이터 바인딩 하는 형식

    @GetMapping("/param3")
    @ResponseBody
    //  parameter 가 많을 경우 객체로 대체가 가능.
    //  객체에 각 변수에 맞게 알아서 바인딩 (데이터 바인딩)
    //  데이터 바인딩 조건 : 기본생성자, setter
    //  http://localhost:8080/hello/param3?name=honGilDong3&email=honGilDong3@naver.com&password=1234&age=35
    //  @ModelAttribute 생략가능
    public Hello param3(@ModelAttribute Hello hello, @RequestParam(value = "age")String inputAge) {
        System.out.println(hello.toString());
        System.out.println(inputAge);
        return hello;
    }






    // ##### case6 : 서버에서 화면에 데이터를 넣어 사용자에게 return(model 객체 사용)
    @GetMapping("/model-param")
    //  http://localhost:8080/hello/model-param?name=hongildong
    public String modelParam(@RequestParam(value = "name")String inputName, Model model) {
        //  model 객체에 name 이라는 키값에 value 를 세팅하면 해당 key:value 는 화면으로 전달
        model.addAttribute("name", inputName);
        return "helloWorld";
    }




    //  ##### case7 : pathVariable 방식을 통해 사용자로부터 값을 받아 화면 리턴
    //  pathVariable 방식은 url 을 통해 자원의 구조를 명확하게 표현함으로,
    //  좀 더 restful(http 통신 원칙 중 하나, 자원의 구조를 명확하게) 한 형식
    //  변수 하나만 받을 때 사용하자. 여러 개를 받아야 하는 상황에서는 파라미터 방식을 사용
    //  http://localhost:8080/hello/model-path/hongildong
    @GetMapping("/model-path/{inputName}")
    public String modelPath(@PathVariable String inputName, Model model) {
        model.addAttribute("name", inputName);
        return "helloWorld";
    }


    @GetMapping("/form-view")
    //  사용자에게 name, email, password 를 입력할 수 있는 화면을 주는 메서드 정의
    public String formView() {
        //  post 요청 (사용자 입장에서 서버에 데이터를 주는 상황)
        //  case1. url 인코딩 방식 (text only) 전송
        //  사용자는 화면을 먼저 받아야 입력할 수 있다.
        //  서버는 화면을 먼저 줘야되.
        return "form-view";
    }

    // GetMapping 과 같은 url 패턴 사용도 가능
    @PostMapping("/form-post1")
    @ResponseBody
    public String formPost1(Hello hello) {
        System.out.println(hello.toString());
        return "ok";
    }

    // GetMapping 과 같은 url 패턴 사용도 가능
    @PostMapping("/form-post2")
    @ResponseBody
    public String formPost2(Hello hello) {
        System.out.println(hello.toString());
        return "ok";
    }



    //  case2. multipart/from-data 방식(text 와 파일) 전송
    //  http://localhost:8080/hello/form-file-post

    @GetMapping("/form-file-post")
    public String formFileView() {
        return "form-file-view";
    }

    @PostMapping("/form-file-post")
    @ResponseBody
    public String formFileHandle(Hello hello, @RequestParam(value = "photo") MultipartFile photo) {
        System.out.println(hello);
        System.out.println(photo.getOriginalFilename());
        return "ok";
    }




    //  case3.  js를 활용한 form 데이터 전송 (text)
    //  http://localhost:8080/hello/axios-form-view
    @GetMapping("/axios-form-view")
    public String axiosFormView() {
        return "axios-form-view";
    }

    @PostMapping("/axios-form-view")
    @ResponseBody
    public String axiosFormPost(Hello hello) {
        System.out.println(hello);
        return "ok";
    }



    //  case4.  js를 활용한 form 데이터 전송 (+file)
    //  http://localhost:8080/hello/axios-form-file-view
    @GetMapping("/axios-form-file-view")
    public String axiosFormFileView() {
        return "axios-form-file-view";
    }

    @PostMapping("/axios-form-file-view")
    @ResponseBody
    public String axiosFormFileViewPost(Hello hello, @RequestParam(value = "file")MultipartFile file) {
        System.out.println(hello);
        System.out.println(file.getOriginalFilename());
        return "ok";
    }



    //  case5.  js를 활용한 json 데이터 전송
    //  http://localhost:8080/hello/axios-json-view
    //  url 패턴 : axios-json-view, 화면명 : axios-json-view, get 요청 메서드 : 동일
    //  post 요청 메서드 : axiosJsonPost

    @GetMapping("axios-json-view")
    public String axiosJsonView() {
        return "axios-json-view";
    }

    @PostMapping("axios-json-view")
    @ResponseBody
    //  json 으로 전송한 데이터를 받을 때에는 @RequestBody 어노테이션 사용
    public String axiosJsonPost(@RequestBody Hello hello) {
        System.out.println(hello.toString());
        return "ok";
    }




    //  case6.  js를 활용한 json 데이터 전송 (+file)
    //  http://localhost:8080/hello/axios-json-file-view
    @GetMapping("axios-json-file-view")
    public String axiosJsonFileView() {
        return "axios-json-file-view";
    }

    @PostMapping("axios-json-file-view")
    @ResponseBody
    //  public String axiosJsonFilePost(@RequestParam("hello") String helloParam, @RequestParam(value = "file") MultipartFile file) throws JsonProcessingException {
    //    ObjectMapper objectMapper = new ObjectMapper();
    //    Hello hello = objectMapper.readValue(helloParam, Hello.class);

    //  formData 를 통해 json , file 을 처리할 때 RequestPart 어노테이션을 많이 사용한다.
    public String axiosJsonFilePost(@RequestPart("hello") Hello hello, @RequestPart("file") MultipartFile file) {
        System.out.println(hello);
        System.out.println("file.getOriginalFilename() = " + file.getOriginalFilename());
        return "ok";
    }







    //  case7.  js를 활용한 json 데이터 전송 (+multi files)
    //  http://localhost:8080/hello/axios-json-multi-file-view
    @GetMapping("/axios-json-multi-file-view")
    public String axiosJsonMultiFileView() {
        return "axios-json-multi-file-view";
    }

    @PostMapping("/axios-json-multi-file-view")
    @ResponseBody
    public String axiosJsonMultiFilePost(
            @RequestPart("hello") Hello hello
            , @RequestPart("files") List<MultipartFile> files) {
        System.out.println(hello);
        for (MultipartFile file : files) {
            System.out.println(file.getOriginalFilename());
        }
        return "ok";
    }





    //  case8. 중첩된 JSON 데이터 처리
    @GetMapping("/axios-nested-json-view")
    public String axiosNestedJsonView() {
        return "axios-nested-json-view";
    }

    @PostMapping("/axios-nested-json-view")
    @ResponseBody
    public String axiosNestedJsonViewPost(@RequestBody Student student) {
        System.out.println(student);
        return "ok";
    }



    //
}