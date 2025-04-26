package com.yhdc.account_service.transaction;


import com.yhdc.account_service.data.Role;
import com.yhdc.account_service.data.User;
import com.yhdc.account_service.data.UserStatus;
import com.yhdc.account_service.object.UserCreateRecord;
import com.yhdc.account_service.object.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Slf4j
@Service
public class DataConverter {

    /**
     * CONVERT USER OBJECT TO DTO
     *
     * @param user
     */
    public UserDto convertUserToDto(User user) {
        log.info("Converting to userDto...");
        try {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId().toString());
            userDto.setUsername(user.getUsername());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setEmail(user.getEmail());
            userDto.setPhone(user.getPhone());
            if (!user.getRoleSet().isEmpty()) {
                userDto.setRole(user.getRoleSet().iterator().next().getRoleName());
            }
            userDto.setStatus(user.getStatus() != null ? user.getStatus().name() : UserStatus.SUSPENDED.name());
            userDto.setCreatedAt(user.getCreatedAt());
            userDto.setModifiedAt(user.getModifiedAt());
            return userDto;

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    /**
     * CONVERT DTO TO USER OBJECT
     *
     * @param userCreateRecord
     * @param role
     */
    public User convertDtoToUser(UserCreateRecord userCreateRecord, String passwordEnc, Role role) {

        User user = new User();
        user.setUsername(userCreateRecord.username());
        user.setPassword(passwordEnc);
        user.setFirstName(userCreateRecord.firstName());
        user.setLastName(userCreateRecord.lastName());
        user.setAddress(userCreateRecord.address());
        user.setEmail(userCreateRecord.email());
        user.setPhone(userCreateRecord.phone());
        // ROLE
        HashSet<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        user.setRoleSet(roleSet);
        return user;
    }
}
