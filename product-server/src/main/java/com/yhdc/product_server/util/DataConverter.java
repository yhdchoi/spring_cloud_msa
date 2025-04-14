package com.yhdc.product_server.util;

import com.yhdc.product_server.object.ProductDto;
import com.yhdc.product_server.transaction.Product;
import com.yhdc.product_server.type.ProductStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class DataConverter {

    /**
     * CONVERT PRODUCT OBJECT TO DTO
     *
     * @param product
     */
    public ProductDto convertProductToDto(Product product) {
        try {
            ProductDto productDto = new ProductDto();
            productDto.setProductId(product.getId());
            productDto.setUserId(product.getUserId());
            productDto.setStoreId(product.getStoreId());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setPrice(product.getPrice());
            productDto.setStatus(product.getStatus() != null ? product.getStatus().selection() : ProductStatus.SUSPENDED.selection());
            productDto.setCreatedAt(dateTimeConverter(product.getCreatedAt()));
            productDto.setModifiedAt(dateTimeConverter(product.getModifiedAt()));
            return productDto;

        } catch (Exception e) {
            log.error("location: {}, error: {}", "Error converting product to DTO...", e.getMessage());
            throw new RuntimeException(e);
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
