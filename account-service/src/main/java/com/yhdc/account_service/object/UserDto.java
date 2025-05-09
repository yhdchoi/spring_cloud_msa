package com.yhdc.account_service.object;

import lombok.Data;

@Data
public class UserDto {

    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String role;
    private String status;

    private String createdAt;
    private String modifiedAt;
}
