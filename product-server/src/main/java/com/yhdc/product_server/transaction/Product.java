package com.yhdc.product_server.transaction;

import com.yhdc.product_server.type.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Product {

    @Id
    private String id;

    // Seller
    private String userId;

    private String storeId;

    private String name;

    private String description;

    private String price;

    private ProductStatus status;

    private String inventory;

}
