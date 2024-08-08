package com.beyond.basic.repository;
//
//import com.beyond.basic.domain.Member;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
////  SpringDataJpa Repository 가 되기 위해서는 JpaRepository 를 상속해야하고,
////  상속 시에 Entity 명(테이블명)과 entity 의 pk 타입을 명시
////  jpaRepository 를 상속함으로서 jpa 기능 상속
////  hibernate 가 jpa 를 구현하고 있음.
////  런타임시점에 사용자가 인터페이스에 정의한 메소드를 프록시(대리) 객체가 리플렉션 기술을 통해 메소드를 구현
//public interface MemberSpringDataJpaRepository extends MemberRepository, JpaRepository<Member, Long> {
//}