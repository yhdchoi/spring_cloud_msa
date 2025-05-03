package com.yhdc.account_service.transaction;

import com.yhdc.account_service.data.*;
import com.yhdc.account_service.object.UserCreateRecord;
import com.yhdc.account_service.object.UserDto;
import com.yhdc.account_service.object.UserPatchRecord;
import com.yhdc.account_service.object.UserPutRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static com.yhdc.account_service.transaction.Constants.USER_MANAGER;
import static com.yhdc.account_service.transaction.Constants.USER_SELLER;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DataConverter dataConverter;
    private final PageProducer pageProducer;
    private final PasswordEncoder passwordEncoder;


    /**
     * CREATE USER
     *
     * @param userCreateRecord
     * @implNote Creates a new user account
     */
    @Transactional
    public ResponseEntity<?> registerUser(UserCreateRecord userCreateRecord) {

        try {
            // Find all the roles
            List<Role> roleList = roleRepository.findAll();
            if (roleList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            final String passwordEnc = passwordEncoder.encode(userCreateRecord.password());

            final Role role = switch (userCreateRecord.role()) {
                case USER_SELLER ->
                        roleList.stream().filter(item -> item.getRoleType() == RoleType.ROLE_SELLER).findFirst().get();
                case USER_MANAGER ->
                        roleList.stream().filter(item -> item.getRoleType() == RoleType.ROLE_MANAGER).findFirst().get();
                default -> roleList.stream().filter(item -> item.getRoleType() == RoleType.ROLE_USER).findFirst().get();
            };

            // Save a new user
            User newUser = userRepository.save(dataConverter.convertDtoToUser(userCreateRecord, passwordEnc, role));
            UserDto userDto = dataConverter.convertUserToDto(newUser);

            return new ResponseEntity<>(userDto, HttpStatus.CREATED);

        } catch (NoSuchElementException nse) {
            return new ResponseEntity<>("Role not allowed", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unable to create user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * USER DETAIL
     *
     * @param userId
     */
    @Transactional(readOnly = true)
    public ResponseEntity<?> detailUser(String userId) {

        User user = userRepository.findById(UUID.fromString(userId)).orElse(null);
        UserDto userDto = dataConverter.convertUserToDto(user);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }


    /**
     * USER PAGE
     *
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param sortOrder
     */
    @Transactional(readOnly = true)
    public ResponseEntity<?> listUser(String pageNo, String pageSize, String sortBy, String sortOrder) {
        try {
//            final Pageable pageable = pageProducer.getPageable(pageNo, pageSize, sortBy, sortOrder);
            Pageable pageable = PageRequest.of(Integer.parseInt(pageNo), Integer.parseInt(pageSize), Sort.by(Sort.Direction.DESC, sortOrder));
            Page<User> userPage = userRepository.findAll(pageable);
            Page<UserDto> userDtoPage = userPage.map(dataConverter::convertUserToDto);
//            List<User> userList = userRepository.findAll();
            return new ResponseEntity<>(userDtoPage, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Unable to pull data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * SEARCH USER PAGE
     *
     * @param keyword
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param sortOrder
     */
    @Transactional(readOnly = true)
    public ResponseEntity<?> searchUser(String keyword, String pageNo, String pageSize, String sortBy, String sortOrder) {


        try {
            final Pageable pageable = pageProducer.getPageable(pageNo, pageSize, sortBy, sortOrder);
            log.info("Pageable: {}", pageable);

            Page<User> userPage;
            log.info("Keyword: {}", keyword);
            if (keyword.equals("*")) {
                log.info("Listing user... Keyword: {}", keyword);
                userPage = userRepository.findAll(pageable);
            } else {
                log.info("Searching user by keyword: {}", keyword);
                userPage = userRepository.searchUserByKeyword(keyword, pageable);
            }
            log.info("PageSize: {}", userPage.getSize());
            Page<UserDto> userDtoPage = userPage.map(dataConverter::convertUserToDto);
            return new ResponseEntity<>(userDtoPage, HttpStatus.OK);

        } catch (UnexpectedRollbackException uerbe) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Unable to perform search", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * UPDATE USER DETAIL
     *
     * @param userPutRecord
     * @implNote Updates none relational fields only.
     */
    @Transactional
    public ResponseEntity<?> updateUser(UserPutRecord userPutRecord) {
        try {
            User user = userRepository.getReferenceById(UUID.fromString(userPutRecord.id()));
            user.setUsername(userPutRecord.username());
            user.setFirstName(userPutRecord.firstName());
            user.setLastName(userPutRecord.lastName());
            user.setEmail(userPutRecord.email());
            user.setPhone(userPutRecord.phone());
            userRepository.save(user);

            return detailUser(userPutRecord.id());
        } catch (Exception e) {
            return new ResponseEntity<>("Unable to update user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * PATCH USER DETAIL
     *
     * @param userPatchRecord
     * @implNote Patches the field specified only
     */
    @Transactional
    public ResponseEntity<?> updateUserPassword(UserPatchRecord userPatchRecord) {
        try {
            User user = userRepository.getReferenceById(UUID.fromString(userPatchRecord.id()));

            if (!passwordEncoder.matches(userPatchRecord.password(), user.getPassword())) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            user.setPassword(passwordEncoder.encode(userPatchRecord.newPassword()));
            userRepository.save(user);

            return detailUser(userPatchRecord.id());
        } catch (Exception e) {
            return new ResponseEntity<>("Unable to update password", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * DELETE USER
     *
     * @param userId
     * @implNote Deletes user account permanently
     */
    @Transactional
    public ResponseEntity<?> deleteUser(String userId) {
        try {
            userRepository.deleteById(UUID.fromString(userId));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Unable to delete user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
