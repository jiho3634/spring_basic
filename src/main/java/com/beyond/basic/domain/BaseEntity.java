package com.beyond.basic.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;


//  Entity 는 상속관계가 불가능한데, 가능하게 해주는 어노테이션
@MappedSuperclass
@Getter
public abstract class BaseEntity {
    //  CamelCase 사용 시 DB 에는 _(underscore)로 생성된다.
    //  DB 에는 current_timestamp 가 생성되지 않음.
    @CreationTimestamp
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updateTime;
}
