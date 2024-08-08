package com.beyond.basic.controller;

import com.beyond.basic.dto.CommonErrorDto;
import com.beyond.basic.dto.CommonResDto;
import com.beyond.basic.dto.MemberReqDto;
import com.beyond.basic.dto.MemberUpdateDto;
import com.beyond.basic.repository.MyMemberRepository;
import com.beyond.basic.service.MemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/rest")
@Api(tags = "회원관리서비스")
public class MemberRestController {

    private final MemberService memberService;
    private final MyMemberRepository memberRepository;

    @Autowired
    public MemberRestController(MemberService memberService, MyMemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/member/text")
    public String memberText() {
        return "OK";
    }

    @PostMapping("/member/create")
    public ResponseEntity<Object> memberCreatePost(@RequestBody MemberReqDto dto) {
        try {
            memberService.memberCreate(dto);
            CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "memberList is successfully taken", null);
            return new ResponseEntity<>(commonResDto, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            CommonErrorDto commonErrorDto = new CommonErrorDto(HttpStatus.BAD_REQUEST.value(), "Bad Request");
            return new ResponseEntity<>(commonErrorDto, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/member/list")
    public ResponseEntity<CommonResDto> memberList() {
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "memberList is successfully taken", memberService.memberList());
        return new ResponseEntity<>(commonResDto, HttpStatus.CREATED);
    }

    @GetMapping("/member/detail/{id}")
    public ResponseEntity<Object> memberDetail(@PathVariable Long id) {
        CommonResDto commonResDto = null;
        try {
            commonResDto = new CommonResDto(HttpStatus.OK, "memberDetail is successfully found", memberService.memberDetail(id));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new CommonErrorDto(HttpStatus.NOT_FOUND.value(), "Not Found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commonResDto, HttpStatus.OK);
    }

    //  수정은 2가지 요청 방식 : PUT, PATCH 요청

    //  PUT                                     PATCH
    //  @PutMapping                             @PatchMapping
    //  자원의 전체 수정을 의미                     자원의 일부 수정
    //  모든 필드를 포함한 객체                     수정하려는 필드만 포함한 객체
    //  Idempotent                              Idempotent 가 아닐 수 있음
    //  같은 요청을 여러 번 보내도 결과가 동일
    //  누락된 필드는 null 이나 기본값

    @PatchMapping("/member/pw/update")
    public String memberPasswordUpdate(@RequestBody MemberUpdateDto dto) {
        memberService.pwUpdate(dto);
        return "ok";
    }

    @DeleteMapping("/member/delete/{id}")
    public String memberDelete(@PathVariable Long id) {
        memberService.delete(id);
        return "ok";
    }

    //  lazy 지연 로딩, eager 즉시 로딩 테스트
    @GetMapping("/member/post/all")
    public void memberPostAll() {
        System.out.println(memberRepository.findAll());
    }
}