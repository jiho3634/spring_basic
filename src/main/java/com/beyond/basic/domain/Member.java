package com.beyond.basic.domain;

import com.beyond.basic.dto.MemberDetailResDto;
import com.beyond.basic.dto.MemberResDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

//  JPA EntityManager 로 관리하기 위한 Entity 어노테이션. 관리 위임.
//  해당 클래스명으로 테이블 및 컬럼을 자동 생성하고 각종 설정 정보 위임.
@EqualsAndHashCode(callSuper = true)
@Entity
@Data

//  모든 인자를 셋팅해가며 객체를 활용하기엔 힘들다
//@AllArgsConstructor

//  기본생성자는 JPA 에서 필수
@NoArgsConstructor
public class Member extends BaseEntity {

    //  pk 설정
    @Id

    //  IDENTITY    :   auto_increment 설정
    //  auto    :   jpa 자동으로 적절한 전략을 선택하도록 맡김.
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    //  Long 은 bigint 로 변환
    private Long id;

    //  String 은 varchar(255)로 기본으로 변환, name 변수명이 name 컬럼명으로 변환.
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(name = "password")
    //  passWord => pass_word
    private String password;

    //  일반적으로 부모엔터티(자식 객체에 영향을 끼칠 수 있는 엔터티) 에 cascade 옵션을 설정
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Post> posts;

    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void updatePw(String password) {
        this.password = password;
    }

    public MemberDetailResDto detFromEntity() {
        String newCreatedTime
                = this.getCreatedTime().getYear() + "년 "
                + this.getCreatedTime().getMonthValue() + "월 "
                + this.getCreatedTime().getDayOfMonth() + "일";
        return new MemberDetailResDto(this.id, this.name, this.email, this.password, newCreatedTime);
    }

    public MemberResDto listFromEntity() {
        return new MemberResDto(this.id, this.name, this.email);
    }
}