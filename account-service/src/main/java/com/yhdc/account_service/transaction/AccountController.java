package com.yhdc.account_service.transaction;

import com.yhdc.account_service.object.UserCreateRecord;
import com.yhdc.account_service.object.UserPatchRecord;
import com.yhdc.account_service.object.UserPutRecord;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountServiceImpl accountService;


    /**
     * REGISTER A NEW USER ACCOUNT
     *
     * @param userCreateRecord
     * @apiNote
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserCreateRecord userCreateRecord) {
        return accountService.registerUser(userCreateRecord);
    }


    /**
     * LOAD USER ACCOUNT DETAIL
     *
     * @param userId
     */
    @GetMapping("/detail")
    public ResponseEntity<?> detailUser(
            @RequestParam(value = "userId") @NotBlank(message = "UserID is mandatory field") String userId) {
        return accountService.detailUser(userId);
    }


    /**
     * LIST/SEARCH USER ACCOUNT
     *
     * @param keyword
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param sortOrder
     * @apiNote If search keyword param is
     */
    @GetMapping("/search")
    public ResponseEntity<?> searchUser(@RequestParam(required = false, defaultValue = "*", value = "keyword") String keyword,
                                        @RequestParam(defaultValue = "0", value = "pageNo") String pageNo,
                                        @RequestParam(defaultValue = "10", value = "pageSize") String pageSize,
                                        @RequestParam(defaultValue = "DESC", value = "pageBy") String sortBy,
                                        @RequestParam(defaultValue = "createdAt", value = "pageOrder") String sortOrder) {
        return accountService.searchUser(keyword, pageNo, pageSize, sortBy, sortOrder);
    }


    /**
     * UPDATE USER ACCOUNT
     *
     * @param userPutRecord
     * @apiNote Update user account detail except password
     */
    @PutMapping("/put")
    public ResponseEntity<?> updateUser(@RequestBody UserPutRecord userPutRecord) {
        return accountService.updateUser(userPutRecord);
    }


    /**
     * UPDATE USER PASSWORD
     *
     * @param userPatchRecord
     * @apiNote Updates user password only
     */
    @PatchMapping("/patch")
    public ResponseEntity<?> updateUserPassword(@RequestBody UserPatchRecord userPatchRecord) {
        return accountService.updateUserPassword(userPatchRecord);
    }


    /**
     * DELETE USER ACCOUNT
     *
     * @param userId
     * @apiNote Permanently deletes user account and all related data
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestParam @NotBlank(message = "UserID is mandatory field") String userId) {
        return accountService.deleteUser(userId);
    }
}
