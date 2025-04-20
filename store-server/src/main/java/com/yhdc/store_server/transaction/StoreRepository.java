package com.yhdc.store_server.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends MongoRepository<Store, Long> {

    /**
     * SEARCH FOR STORE
     *
     * @param keyword
     * @param pageable
     */
    Page<Store> findAllByNameContainingIgnoreCase(String keyword, Pageable pageable);

}
