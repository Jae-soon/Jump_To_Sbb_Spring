package com.ll.exam.sbb.user;

import lombok.Getter;

@Getter
public enum UserRole { // 코드의 안정성을 높히기 위해 enum사용
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}

//class Main {
//    public static void main(String[] args) {
//        System.out.println("하이");
//
//        UserRole ADMIN = new UserRole("ROLE_ADMIN"); // ADMIN("ROLE_ADMIN")
//        UserRole USER = new UserRole("ROLE_USER"); // USER("ROLE_USER");
//    }
//}
//
//class UserRole {
//    UserRole(String value) {
//        this.value = value;
//    }
//
//    private String value;
//}