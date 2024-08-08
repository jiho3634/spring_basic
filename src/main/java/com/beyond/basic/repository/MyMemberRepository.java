package com.beyond.basic.repository;

import com.beyond.basic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyMemberRepository extends JpaRepository<Member, Long> {
    //  findBy 컬럼명의 규칙으로 자동으로 where 조건문을 사용한 쿼리 생성
    //  그 외 : findByNameAndEmail, findByNameOrEmail
    //  findByAgeBetween(int start, int end)
    //  findByAgeLessThan(int age)
    //  findByAgeGreaterThan(int age)
    //  findByAgeIsNull(), findByAgeIsNotNull()
    //  findAllOrderByEmail();
    Optional<Member> findByEmail(String email);
}
