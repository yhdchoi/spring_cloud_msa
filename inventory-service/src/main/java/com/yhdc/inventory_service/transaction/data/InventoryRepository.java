package com.yhdc.inventory_service.transaction.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, Long> {

    Optional<Inventory> findByProductIdAndSkuCode(String productId, String skuCode);

    void deleteByProductIdAndSkuCode(String productId, String skuCode);
}
