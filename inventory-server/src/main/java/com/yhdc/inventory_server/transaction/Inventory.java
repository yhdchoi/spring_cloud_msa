package com.yhdc.inventory_server.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("inventory")
public class Inventory {

    @Id
    private String id;

    @Field(name = "product_id")
    private String productId;

    @Field(name = "sku_code")
    private String skuCode;

    private Long quantity;

    @Field(name = "created_at")
    @CreatedDate
    private OffsetDateTime createdAt;

    @Field(name = "modified_at")
    @LastModifiedDate
    private OffsetDateTime modifiedAt;

    /**
     * We also need a @Version field in our documents, otherwise there will be problems in combination
     * with the Id field. If the id is pre-filled by our application, auditing assumes that the document
     * already existed in the database and @CreatedDate is not set. With the version field however, the
     * life cycle of our entity is correctly captured.
     */
    @Version
    private Integer version;
}
