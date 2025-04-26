package com.yhdc.account_service.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    /**
     * USER SEARCH BY USERNAME
     *
     * @param username
     */
    Optional<User> findByUsername(String username);

    /**
     * USER SEARCH BY KEYWORD
     *
     * @param keyword
     * @param pageable
     */
    @Query(value = "FROM User WHERE UPPER(username) LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR UPPER(firstName) LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR UPPER(lastName) LIKE CONCAT('%', UPPER(?1), '%')" +
            "OR UPPER(address) LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR UPPER(email) LIKE CONCAT('%', UPPER(?1), '%')" +
            "OR UPPER(phone) LIKE CONCAT('%', UPPER(?1), '%')")
    Page<User> searchUserByKeyword(String keyword, Pageable pageable);


    /**
     * USER SEARCH BY ROLE
     *
     * @param role
     * @param pageable
     */
    Page<User> findAllByRoleSetContaining(Role role, Pageable pageable);

}
