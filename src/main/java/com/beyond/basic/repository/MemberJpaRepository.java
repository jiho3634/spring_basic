//package com.beyond.basic.repository;
//
//import com.beyond.basic.domain.Member;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public class MemberJpaRepository implements MemberRepository {
//
////    EntityManager 는 JPA 의 핵심클래스(객체)
////    Entity 의 생명주기를 관리, 데이터베이스와의 모든 인터페이싱을 책임
////    즉, Entity 대상으로 CRUD 하는 기능을 제공
//
//    @Autowired
//    private EntityManager entityManager;
//
//    @Override
//    public Member save(Member member) {
//        //  persist :
//        //  전달된 Entity 가 EntityManager 의 관리상태가 되도록 만들어주고,
//        //  Transaction 이 commit 될 때 DataBase 에 저장
//        entityManager.persist(member);
//        return member;
//    }
//
//    @Override
//    public List<Member> findAll() {
//        //  jpql    :   jpq 의 rqw 객체지향 쿼리 문법
//        //  컴파일 시 문법에러 X, Spring Data Jpa 는 컴파일 시에 문법 에러를 낸다.
//        return entityManager
//                .createQuery("select m from Member m", Member.class)
//                .getResultList();
//    }
////
////    public Member findByEmail(String email) {
////        return entityManager
////                .createQuery("select m from Member m where m.email = :email", Member.class)
////                .setParameter("email", email)
////                .getSingleResult();
////    }
////
//    @Override
//    public Optional<Optional<Member>> findById(Long id) {
//        //  entityManager 를 통해 find(리턴타입 클래스 지정 및 매개변수로 pk 필요)
//        return Optional.ofNullable(entityManager.find(Member.class, id));
//    }
//}
