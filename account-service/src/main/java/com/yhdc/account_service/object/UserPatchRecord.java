package com.yhdc.account_service.object;

import java.util.Objects;

public record UserPatchRecord(String userId, String password, String newPassword) {

    public UserPatchRecord {
        Objects.requireNonNull(userId, "User ID must not be null");
        Objects.requireNonNull(password, "Password must not be null");
        Objects.requireNonNull(newPassword, "New password must not be null");
    }

}
