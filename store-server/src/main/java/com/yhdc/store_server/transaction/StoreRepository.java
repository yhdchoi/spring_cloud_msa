package com.yhdc.store_server.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StoreRepository extends CrudRepository<Store, UUID> {

    /**
     * PAGE ALL STORE
     *
     * @param pageable
     */
    Page<Store> findAll(Pageable pageable);


    /**
     * SEARCH FOR STORE
     *
     * @param keyword
     * @param pageable
     */
    @Query(value = "FROM Store WHERE UPPER(name) LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR UPPER(description) LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR UPPER(status) LIKE CONCAT('%', UPPER(?1), '%')")
    Page<Store> searchStoreByKeyword(String keyword, Pageable pageable);

}
