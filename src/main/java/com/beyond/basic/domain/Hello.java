package com.beyond.basic.domain;

import lombok.*;

//@Getter
//@Setter
//@ToString
//  getter, setter, toString 등등 포함하는 어노테이션
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hello {
    String name;
    String email;
    String password;

    public Hello(HelloBuilder helloBuilder) {
        this.name = helloBuilder.name;
        this.email = helloBuilder.email;
        this.password = helloBuilder.password;
    }

    //  @Builder 패턴 직접 구현
    //  빌더 적용 대상 생성자가 필요

    public static class HelloBuilder {
        private String name;
        private String email;
        private String password;

        public static HelloBuilder builder() {
            return new HelloBuilder();
        }

        public HelloBuilder name(String name) {
            this.name = name;
            return this;
        }

        public HelloBuilder email(String email) {
            this.email = email;
            return this;
        }

        public HelloBuilder password(String password) {
            this.password = password;
            return this;
        }

        public Hello build() {
            return new Hello(this);
        }
    }
}
