package com.yhdc.product_server.transaction;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, Long> {

    /**
     * PAGE ALL PRODUCTS IN A STORE
     *
     * @param storeId
     * @param pageable
     */
    Page<Product> findAllByStoreId(String storeId, Pageable pageable);

    /**
     * SEARCH A STORE PRODUCTS BY KEYWORD
     *
     * @param storeId
     * @param keyword
     * @param pageable
     */
//    @Query("{ 'storeId' :  ?0, 'name': ?1 }")
    Page<Product> findAllByStoreIdAndNameContainingIgnoreCase(String storeId, String keyword, Pageable pageable);

    /**
     * SEARCH ALL PRODUCTS BY KEYWORD ONLY
     *
     * @param keyword
     * @param pageable
     */
//    @Query("{ 'name' :  ?0 }")
    Page<Product> findAllByNameContainingIgnoreCase(String keyword, Pageable pageable);

}
