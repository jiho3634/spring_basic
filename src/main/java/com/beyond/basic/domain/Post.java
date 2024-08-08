package com.beyond.basic.domain;

import com.beyond.basic.dto.PostResDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    //  연관관계의 주인은 fk를 가지고 있는 자식 엔터티인 post
    //  1:1 의 경우 OneToOne 어노테이션을 달고, unique=true 로 설정.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    //  JPA 의 영속성(Persistence) 컨텍스트에 의해 Member 가 관리
    private Member member;

    public PostResDto fromEntity() {
        return new PostResDto(this.id, this.title);
    }
}