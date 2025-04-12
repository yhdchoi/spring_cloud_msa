package com.yhdc.product_server.util;

import com.yhdc.product_server.object.ProductDto;
import com.yhdc.product_server.transaction.Product;
import com.yhdc.product_server.type.ProductStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@Slf4j
@Service
public class DataConverter {

    /**
     * FORMAT DATE TO FORMATTED STRING
     *
     * @param localDateTime
     */
    public String dateConverter(LocalDateTime localDateTime) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(localDateTime);
    }


    /**
     * CONVERT PRODUCT OBJECT TO DTO
     *
     * @param product
     */
    public ProductDto convertProductToDto(Product product) {
        try {
            ProductDto productDto = new ProductDto();
            productDto.setProductId(product.getId().toString());
            productDto.setUserId(product.getUserId().toString());
            productDto.setStoreId(product.getStoreId().toString());
            productDto.setName(product.getName());
            productDto.setDescription(product.getDescription());
            productDto.setPrice(product.getPrice());
            productDto.setStatus(product.getStatus() != null ? product.getStatus().selection() : ProductStatus.SUSPENDED.selection());

            return productDto;

        } catch (Exception e) {
            log.error("location: {}, error: {}", "Error converting product to DTO...", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
