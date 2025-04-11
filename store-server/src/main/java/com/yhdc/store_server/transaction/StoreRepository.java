package com.yhdc.store_server.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StoreRepository extends CrudRepository<Store, UUID>, StoreDslRepository {

    Page<Store> findAll(Pageable pageable);
}
