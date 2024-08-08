//package com.beyond.basic.repository;
//
//import com.beyond.basic.domain.Member;
//import org.springframework.stereotype.Repository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
////  Repository 계층을 싱글톤 객체로 생성
//@Repository
//public class MemberMemoryRepository implements MemberRepository {
//
//    private final List<Member> memberList = new ArrayList<>();
//
//    @Override
//    public Member save(Member member) {
//        memberList.add(member);
//
//        //  데이터베이스에 추가한 후에 다시 조회해서 반환해 주어야 하지만 여기선 생략한다.
//        return member;
//    }
//
//    @Override
//    public List<Member> findAll() {
//        return new ArrayList<>(memberList);
//    }
//
//    @Override
//    public Optional<Optional<Member>> findById(Long id) {
//        return null;
//    }
//}
