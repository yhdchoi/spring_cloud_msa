package com.yhdc.account_server.object;

import java.util.Objects;

public record UserPatchRecord(String id, String password, String newPassword) {

    public UserPatchRecord {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(password, "password must not be null");
        Objects.requireNonNull(newPassword, "new password must not be null");
    }

}
