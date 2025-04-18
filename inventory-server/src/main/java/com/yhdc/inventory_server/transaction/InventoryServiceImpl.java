package com.yhdc.inventory_server.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InventoryServiceImpl {

    private final InventoryRepository inventoryRepository;


    /**
     * CREATE A NEW INVENTORY
     *
     * @param inventoryCreateRecord
     */
    public ResponseEntity<?> createNewInventory(InventoryCreateRecord inventoryCreateRecord) {

        Inventory inventory = new Inventory();
        inventory.setProductId(inventoryCreateRecord.productId());
        inventory.setSkuCode(inventoryCreateRecord.skuCode());
        inventory.setQuantity(Long.valueOf(inventoryCreateRecord.quantity()));

        Inventory savedInventory = inventoryRepository.save(inventory);

        return new ResponseEntity<>(savedInventory.getId(), HttpStatus.CREATED);
    }


    /**
     * FIND INVENTORY
     *
     * @param productId
     * @param skuCode
     */
    private Inventory getInventory(String productId, String skuCode) {
        Optional<Inventory> inventory = inventoryRepository.findByProductIdAndSkuCode(productId, skuCode);
        return inventory.orElse(null);
    }


    /**
     * DETAIL INVENTORY
     *
     * @param inventoryId
     */
    public ResponseEntity<?> detailInventory(String inventoryId) {
        Optional<Inventory> inventory = inventoryRepository.findById(inventoryId);
        if (inventory.isPresent()) {
            return new ResponseEntity<>(inventory.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * INCREASE INVENTORY QUANTITY
     *
     * @param inventoryCommonRecord
     */
    public ResponseEntity<?> increaseQuantity(InventoryCommonRecord inventoryCommonRecord) {
        Inventory inventory = getInventory(inventoryCommonRecord.productId(), inventoryCommonRecord.skuCode());
        if (inventory != null) {
            if (inventory.getQuantity().compareTo(Long.valueOf(inventoryCommonRecord.quantity())) < 0) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            inventory.setQuantity(inventory.getQuantity() - Long.parseLong(inventoryCommonRecord.quantity()));
            Inventory savedInventory = inventoryRepository.save(inventory);
            return new ResponseEntity<>(savedInventory.getId(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * DECREASE INVENTORY QUANTITY
     *
     * @param inventoryCommonRecord
     */
    public ResponseEntity<?> decreaseQuantity(InventoryCommonRecord inventoryCommonRecord) {
        Inventory inventory = getInventory(inventoryCommonRecord.productId(), inventoryCommonRecord.skuCode());
        if (inventory != null) {
            inventory.setQuantity(inventory.getQuantity() + Long.parseLong(inventoryCommonRecord.quantity()));
            Inventory savedInventory = inventoryRepository.save(inventory);
            return new ResponseEntity<>(savedInventory.getId(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * UPDATE SKU
     *
     * @param productId
     * @param skuCode
     */
    public ResponseEntity<?> updateSku(String productId, String skuCode, String newSkuCode) {
        Inventory inventory = getInventory(productId, skuCode);
        if (inventory != null) {
            inventory.setSkuCode(newSkuCode);
            Inventory savedInventory = inventoryRepository.save(inventory);
            return new ResponseEntity<>(savedInventory.getId(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * DELETE INVENTORY
     *
     * @param productId
     * @param skuCode
     * @implNote When product is deleted
     */
    public ResponseEntity<?> deleteInventory(String productId, String skuCode) {
        try {
            inventoryRepository.deleteByProductIdAndSkuCode(productId, skuCode);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * CHECK INVENTORY
     */
    public boolean checkStock(String productId, String skuCode, String quantity) {
        Inventory inventory = getInventory(productId, skuCode);
        if (inventory != null) {
            return inventory.getQuantity().compareTo(Long.valueOf(quantity)) < 0;
        } else {
            return false;
        }
    }
}
