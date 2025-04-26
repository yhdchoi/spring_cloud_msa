package com.yhdc.account_service.data;

public enum RoleType {

    ROLE_ADMIN("ADMIN"),
    ROLE_MANAGER("MANAGER"),
    ROLE_SELLER("SELLER"),
    ROLE_USER("USER");

    private final String name;

    RoleType(String name) {
        this.name = name;
    }

}
