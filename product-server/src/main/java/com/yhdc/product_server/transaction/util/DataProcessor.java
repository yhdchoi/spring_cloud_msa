package com.yhdc.product_server.transaction.util;

import com.yhdc.product_server.transaction.InventoryFeignClient;
import com.yhdc.product_server.transaction.Product;
import com.yhdc.product_server.transaction.object.InventoryCommonRecord;
import com.yhdc.product_server.transaction.object.ProductDto;
import com.yhdc.product_server.transaction.type.ProductStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@Service
public class DataProcessor {

    private final InventoryFeignClient inventoryFeignClient;


    /**
     * CONVERT PRODUCT OBJECT TO DTO
     *
     * @param product
     */
    public ProductDto convertProductToDto(Product product) {
        try {
            ProductDto productDto = new ProductDto();
            productDto.setProductId(String.valueOf(product.getId()));
            productDto.setUserId(product.getUserId());
            productDto.setStoreId(product.getStoreId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setPrice(product.getPrice());
            productDto.setSkuCode(product.getSkuCode());
            productDto.setStatus(product.getStatus() != null ? product.getStatus() : ProductStatus.SUSPENDED.selection());
            productDto.setQuantity(getInventoryDetail(productDto.getProductId(), productDto.getSkuCode()));
            productDto.setCreatedAt(dateTimeConverter(product.getCreatedAt()));
            productDto.setModifiedAt(dateTimeConverter(product.getModifiedAt()));
            return productDto;

        } catch (Exception e) {
            log.error("location: {}, error: {}", "Error converting product to DTO...", e.getMessage());
            throw new RuntimeException(e);
        }
    }


    /**
     * CREATE A NEW INVENTORY
     *
     * @param productId
     * @param skuCode
     * @param quantity
     */
    public boolean inventoryCreator(String productId, String skuCode, String quantity) {

        // Create new inventory through Feign Client
        InventoryCommonRecord inventoryCommonRecord
                = new InventoryCommonRecord(productId, skuCode, quantity);
        ResponseEntity<?> response = inventoryFeignClient.createStock(inventoryCommonRecord);

        if (response.getStatusCode() == HttpStatus.OK) {
            log.info("Product saved successfully!!!");
            return true;
        } else {
            log.error("Failed to save Inventory!!!");
            return false;
        }
    }

    /**
     * INVENTORY DETAIL
     *
     * @param productId
     * @param skuCode
     */
    private String getInventoryDetail(String productId, String skuCode) {
        String response = inventoryFeignClient.getInventoryStock(productId, skuCode);
        if (response.matches("^\\d+$")) {
            return response;
        } else {
            return "n/a";
        }
    }


    /**
     * CONVERTS OFFSET DATE TIME
     *
     * @param dateTime
     */
    private String dateTimeConverter(OffsetDateTime dateTime) {
        final String pattern = "yyyy-MM-dd HH:mm:ss";
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
}
