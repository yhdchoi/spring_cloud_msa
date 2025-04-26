package com.yhdc.product_service.transaction.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("product")
public class Product {

    @Transient
    public static final String SEQUENCE_NAME = "product_sequence";

    @Id
    private Long id;

    // Seller
    @NotBlank
    @Field(name = "user_id")
    private String userId;

    @NotBlank
    @Field(name = "store_id")
    private String storeId;

    @NotBlank
    private String name;

    @NotBlank
    @Field(name = "sku_code")
    private String skuCode;

    private String description;

    @NotBlank
    private String price;

    @NotBlank
    @Size(min = 1, max = 10)
    private String status;

    @Field(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;

    @Field(name = "modified_at")
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    /**
     * We also need a @Version field in our documents, otherwise there will be problems in combination
     * with the Id field. If the id is pre-filled by our application, auditing assumes that the document
     * already existed in the database and @CreatedDate is not set. With the version field however, the
     * life cycle of our entity is correctly captured.
     */
    @Version
    private Integer version;
}
