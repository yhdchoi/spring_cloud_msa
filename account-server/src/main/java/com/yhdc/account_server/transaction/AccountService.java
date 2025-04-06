package com.yhdc.account_server.transaction;

import com.yhdc.account_server.object.UserCreateRecord;
import com.yhdc.account_server.object.UserPatchRecord;
import com.yhdc.account_server.object.UserPutRecord;
import org.springframework.http.ResponseEntity;

public interface AccountService {

    ResponseEntity<?> registerUser(UserCreateRecord userCreateRecord);

    ResponseEntity<?> detailUser(String userId);

    ResponseEntity<?> listUser(String pageNo, String pageSize, String sortBy, String sortOrder);

    ResponseEntity<?> searchUser(String keyword, String pageNo, String pageSize, String sortBy, String sortOrder);

    ResponseEntity<?> updateUser(UserPutRecord userPutRecord);

    ResponseEntity<?> updateUserPassword(UserPatchRecord userPatchRecord);

    ResponseEntity<?> deleteUser(String userId);

}
