package com.beyond.basic.controller;

import com.beyond.basic.dto.CommonResDto;
import com.beyond.basic.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/response/entity")
public class ResponseEntityController {

    //  Case1. @ResponseStatus 방식
    @GetMapping("/annotation1")
    @ResponseStatus(HttpStatus.OK)
    public String annotation1() {
        return "ok";
    }

    @GetMapping("/annotation2")
    @ResponseStatus(HttpStatus.CREATED)
    public String annotation2() {
        //  객체 생성 후 DB 저장 성공했을 때
        Member member = new Member("PARK", "park@naver.com", "12341234");
        return "ok";
    }

    //  Case2. 메서드 체이닝 방식 : ResponseEntity 의 메서드 사용 방식
    @GetMapping("/chaining1")
    public ResponseEntity<Member> Chaining1() {
        Member member = new Member("CHAIN1", "chain1@naver.com", "12341234");
        return ResponseEntity.ok(member);
    }

    @GetMapping("/chaining2")
    public ResponseEntity<Member> Chaining2() {
        Member member = new Member("CHAIN2", "chain2@naver.com", "12341234");
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

    @GetMapping("/chaining3")
    public ResponseEntity<Member> Chaining3() {
        return ResponseEntity.notFound().build();
    }

    //  *** Case3. ResponseEntity 객체를 직접 custom 하여 생성하는 방식 ***
    @GetMapping("/custom1")
    public ResponseEntity<Member> custom1() {
        Member member = new Member("CUSTOM1", "custom1@naver.com", "12341234");
        return new ResponseEntity<>(member, HttpStatus.CREATED);
    }

    @GetMapping("/custom2")
    public ResponseEntity<CommonResDto> custom2() {
        Member member = new Member("CUSTOM2", "custom2@naver.com", "12341234");
        CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "member is successfully created", member);
        return new ResponseEntity<>(commonResDto, HttpStatus.CREATED);
    }
}
