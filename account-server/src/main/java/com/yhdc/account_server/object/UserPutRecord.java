package com.yhdc.account_server.object;

import java.util.Objects;

/**
 * USER PUT DTO
 *
 * @apiNote Updates USER detail for all fields
 */
public record UserPutRecord(String id, String username, String firstName, String lastName, String email, String phone) {

    public UserPutRecord {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(username, " username must not be null");
        Objects.requireNonNull(firstName, "firstName must not be null");
        Objects.requireNonNull(lastName, "lastName must not be null");
        Objects.requireNonNull(email, "email must not be null");
        Objects.requireNonNull(phone, "phone must not be null");
    }

}