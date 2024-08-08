//  C -> S -> R -> DB
//  Controller -> Service -> Repository -> DB
//  사용자에게 받은 데이터를 서비스에 넘겨서 검증 & 업무 처리

package com.beyond.basic.controller;

import com.beyond.basic.dto.MemberDetailResDto;
import com.beyond.basic.dto.MemberReqDto;
import com.beyond.basic.dto.MemberResDto;
import com.beyond.basic.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MemberController {

    //  의존성 주입 (DI) 방법1. 생성자 주입 방식 (가장 많이 사용하는 방식)
    //  생성자 하나인 경우 @Autowired 생략 가능
    //  장점1 : final 을 통해 상수로 사용 가능
    //  장점2 : 다형성 구현 가능
    //  장점3 : 순환 참조 방지

    //  의존성 주입 (DI) 방법2. 필드 주입 방식 (Autowired 만 사용)
    //  단점 : 인터페이스 구현 불가능 => 다형성 구현시 각각의 자식 클래스를 모두 주입해줘야함.

    //  의존성 주입 (DI) 방법3. 어노테이션 (RequiredArgs)을 이용하는 방식
    //  @RequiredArgsConstructor : Class 위에 어노테이션 달아주면 final 변수를 선언만 해도 생성자를 만들어 초기화해준다.
    //  @NonNull : 변수 위에 달아주면 초기화가 강제되는데, 이 때 @RequiredArgsConstructor 를 클래스 위에 달아주면 자동초기화된다.
    //  final (상수화) 키워드가 붙어있는 필드를 대상으로 생성자 생성
    //  단점 : 인터페이스 구현 불가능

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //  http://localhost:8080/
    @GetMapping("/")
    public String home() {
        return "/member/home";
    }


    //  http://localhost:8080/member/create
    @GetMapping("/member/create")
    public String memberCreate() {
        return "/member/member-create";
    }

    @PostMapping("/member/create")
    public String memberCreatePost(Model model, MemberReqDto dto) {
        try {
            memberService.memberCreate(dto);

            //  리다이렉션
            return "redirect:/member/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/member-error";
        }
    }


    //  회원목록조회
    //  http://localhost:8080/member/list

    @GetMapping("/member/list")
    public String memberList(Model model) {
        List<MemberResDto> memberList = memberService.memberList();
        model.addAttribute("memberList", memberList);
        return "member/member-list";
    }

    //  회원상세조회 : memberDetail
    @GetMapping("/member/detail/{id}")
    //  int 또는 long 받을 경우 스프링에서 형변환 (String -> Long)
    public String memberDetail(@PathVariable Long id, Model model) {
        MemberDetailResDto memberDetailResDto = memberService.memberDetail(id);
        model.addAttribute("memberDetailResDto", memberDetailResDto);
        return "/member/member-detail";
    }
}