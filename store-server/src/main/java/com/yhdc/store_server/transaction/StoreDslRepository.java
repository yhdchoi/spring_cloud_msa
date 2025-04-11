package com.yhdc.store_server.transaction;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreDslRepository {

    Page<Store> searchStore(String keyword, Pageable pageable);

}
