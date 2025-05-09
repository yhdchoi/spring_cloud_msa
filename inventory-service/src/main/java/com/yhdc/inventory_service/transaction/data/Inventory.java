package com.yhdc.inventory_service.transaction.data;

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
@Document("inventory")
public class Inventory {

    @Transient
    public static final String SEQUENCE_NAME = "inventory_sequence";

    @Id
    private Long id;

    @Field(name = "product_id")
    private String productId;

    @Field(name = "sku_code")
    private String skuCode;

    private Long quantity;


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
