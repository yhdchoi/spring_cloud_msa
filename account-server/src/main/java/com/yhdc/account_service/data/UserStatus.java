package com.yhdc.account_service.data;

public enum UserStatus {

    ACTIVE("활성"),
    INACTIVE("휴먼"),
    SUSPENDED("정지");

    private final String koMessage;

    UserStatus(String koMessage) {
        this.koMessage = koMessage;
    }

}
