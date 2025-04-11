package com.yhdc.product_server.transaction;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    /**
     * SEARCH ALL PRODUCTS
     *
     * @param pageable
     */
    Page<Product> findAll(Pageable pageable);

    /**
     * PAGE ALL PRODUCTS IN A STORE
     *
     * @param storeId
     * @param pageable
     */
    Page<Product> findAllByStoreId(UUID storeId, Pageable pageable);

    /**
     * SEARCH A STORE PRODUCTS BY KEYWORD
     *
     * @param storeId
     * @param keyword
     * @param pageable
     */
    @Query(value = "FROM Product WHERE storeId == ?1 " +
            "AND UPPER(name) LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR UPPER(description) LIKE CONCAT('%', UPPER(?1), '%')" +
            "OR UPPER(price) LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR UPPER(status) LIKE CONCAT('%', UPPER(?1), '%')" +
            "OR UPPER(stock) LIKE CONCAT('%', UPPER(?1), '%')")
    Page<Product> findAllByStoreIdAndKeyword(UUID storeId, String keyword, Pageable pageable);

    /**
     * SEARCH ALL PRODUCTS BY KEYWORD ONLY
     *
     * @param keyword
     * @param pageable
     */
    @Query(value = "FROM Product WHERE UPPER(name) LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR UPPER(description) LIKE CONCAT('%', UPPER(?1), '%')" +
            "OR UPPER(price) LIKE CONCAT('%', UPPER(?1), '%') " +
            "OR UPPER(status) LIKE CONCAT('%', UPPER(?1), '%')" +
            "OR UPPER(stock) LIKE CONCAT('%', UPPER(?1), '%')")
    Page<Product> findAllByKeyword(String keyword, Pageable pageable);

}
