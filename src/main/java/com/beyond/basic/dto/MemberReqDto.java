package com.beyond.basic.dto;

import com.beyond.basic.domain.Member;
import lombok.Data;

@Data
public class MemberReqDto {
    private String name;
    private String email;
    private String password;

    public Member toEntity() {
        return new Member(name, email, password);
    }
}
