package com.yhdc.account_service.object;

import java.util.Objects;

public record UpdateUserRoleRecord(String id, String userId, String roleId) {

    public UpdateUserRoleRecord {
        Objects.requireNonNull(id, "ID must not be null");
        Objects.requireNonNull(userId, " User ID must not be null");
        Objects.requireNonNull(roleId, "Role must not be null");
    }

}
