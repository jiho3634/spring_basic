//  input 갑의 검증 및 실질적인 비지니스 로직은 서비스 계층에서 수행

package com.beyond.basic.service;

import com.beyond.basic.domain.*;
import com.beyond.basic.dto.MemberDetailResDto;
import com.beyond.basic.dto.MemberReqDto;
import com.beyond.basic.dto.MemberResDto;
import com.beyond.basic.dto.MemberUpdateDto;
import com.beyond.basic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//  Service 계층을 싱글톤 객체로 생성
@Service

//  모든 메서드에 Transaction 어노테이션을 적용, 예외발생 시 RollBack
//  메소드 별로 Transaction 을 적용할 수 있다.
@Transactional(readOnly = true)
public class MemberService {

    //  객체 변수로 선언
    private final MyMemberRepository memberRepository;

    //  싱글톤 객체를 주입 (DI. Dependency Injection)
    @Autowired
    public MemberService(MyMemberRepository memoryRepository) {
        this.memberRepository = memoryRepository;
    }

    public void memberCreate(MemberReqDto dto) {
        if (dto.getPassword().length() < 8) {
            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
        }
        Member member = new Member();
        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setPassword(dto.getPassword());
        memberRepository.save(member);

        //  Transactional 롤백 처리 테스트
        //  @Transactional 이 있으면 save 했던 데이터가 삭제되고, 없으면 예외만 던지고 데이터는 그대로.
        if (member.getName().equals("kim")) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
    }

    public List<MemberResDto> memberList() {
        List<Member> memberList = memberRepository.findAll();
        List<MemberResDto> dtoList = new ArrayList<>();
        for (Member member : memberList)
            dtoList.add(member.listFromEntity());
        return dtoList;
    }

    public MemberDetailResDto memberDetail(Long id) {
        Optional<Member> optMember = memberRepository.findById(id);
        MemberDetailResDto memberDetailResDto = new MemberDetailResDto();
        //  목적1. 클라이언트에게 적절한 예외메시지와 상태코드 전달
        //  목적2. 롤백처리
        Member member = optMember.orElseThrow(() -> new EntityNotFoundException("없는 회원입니다."));
        return member.detFromEntity();
    }

    public void pwUpdate(MemberUpdateDto dto) {
        Member member = memberRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("member is not found"));
        member.updatePw(dto.getPassword());
        //  기존객체를 조회 후 수정시엔 JPA 가 save 로 update 를 실행한다.
        //  save 는 필수가 아니다.
        //  dirtyChecking (변경감지) : jpa 가 특정 엔터티의 변경을 자동으로 인지하고 변경사항을 DB 에 반영하는 것
        //  @Transactional 반드시 필요!
        memberRepository.save(member);
    }

    public void delete(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("member is not found"));
        memberRepository.delete(member);
    }
}