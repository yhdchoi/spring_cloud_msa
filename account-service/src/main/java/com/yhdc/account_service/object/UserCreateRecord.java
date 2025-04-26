package com.yhdc.account_service.object;

import java.util.Objects;

public record UserCreateRecord(String username,
                               String password,
                               String firstName,
                               String lastName,
                               String address,
                               String email,
                               String phone,
                               String role) {

    public UserCreateRecord {
        Objects.requireNonNull(username, " Username must not be null");
        Objects.requireNonNull(password, "Password must not be null");
        Objects.requireNonNull(firstName, "First name must not be null");
        Objects.requireNonNull(lastName, "Last name must not be null");
        Objects.requireNonNull(address, "Address must not be null");
        Objects.requireNonNull(email, "Email must not be null");
        Objects.requireNonNull(phone, "Phone must not be null");
        Objects.requireNonNull(role, "Role must not be null");
    }

}
