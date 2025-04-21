package com.yhdc.inventory_server.transaction;

import com.yhdc.inventory_server.transaction.data.DatabaseSequenceGeneratorService;
import com.yhdc.inventory_server.transaction.data.Inventory;
import com.yhdc.inventory_server.transaction.data.InventoryRepository;
import com.yhdc.inventory_server.transaction.object.InventoryCommonRecord;
import com.yhdc.inventory_server.transaction.object.InventoryCreateRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final DatabaseSequenceGeneratorService databaseSequenceGeneratorService;


    /**
     * FIND INVENTORY
     *
     * @param productId
     * @param skuCode
     */
    @Transactional
    protected Inventory getInventory(String productId, String skuCode) {
        Optional<Inventory> inventory = inventoryRepository.findByProductIdAndSkuCode(productId, skuCode);
        return inventory.orElse(null);
    }


    /**
     * CREATE A NEW INVENTORY
     *
     * @param inventoryCreateRecord
     */
    @Override
    @Transactional
    public ResponseEntity<?> createInventory(InventoryCreateRecord inventoryCreateRecord) {
        try {
            Inventory inventory = new Inventory();
            inventory.setId(databaseSequenceGeneratorService.generateSequence(Inventory.SEQUENCE_NAME));
            inventory.setProductId(inventoryCreateRecord.productId());
            inventory.setSkuCode(inventoryCreateRecord.skuCode());
            inventory.setQuantity(Long.valueOf(inventoryCreateRecord.quantity()));

            final Inventory savedInventory = inventoryRepository.save(inventory);
            return new ResponseEntity<>(savedInventory.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * CHECK INVENTORY STOCK
     */
    @Override
    @Transactional(readOnly = true)
    public String checkInventoryStock(String productId, String skuCode, String quantity) {
        final Inventory inventory = getInventory(productId, skuCode);
        if (inventory != null) {
            boolean isStock = inventory.getQuantity().compareTo(Long.valueOf(quantity)) < 0;
            return isStock ? "true" : "false";
        } else {
            return "false";
        }
    }


    /**
     * GET INVENTORY STOCK
     *
     * @param productId
     */
    @Override
    @Transactional(readOnly = true)
    public String getInventoryStock(String productId, String skuCode) {
        final Inventory inventory = getInventory(productId, skuCode);
        return inventory.getQuantity().toString();
    }


    /**
     * INCREASE INVENTORY QUANTITY
     *
     * @param inventoryCommonRecord
     */
    @Override
    @Transactional
    public ResponseEntity<?> increaseInventoryStock(InventoryCommonRecord inventoryCommonRecord) {
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
    @Override
    @Transactional
    public ResponseEntity<?> decreaseInventoryStock(InventoryCommonRecord inventoryCommonRecord) {
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
    @Override
    @Transactional
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
    @Override
    @Transactional
    public ResponseEntity<?> deleteInventory(String productId, String skuCode) {
        try {
            inventoryRepository.deleteByProductIdAndSkuCode(productId, skuCode);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
