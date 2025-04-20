package com.yhdc.order_server.transaction;

import com.yhdc.order_server.transaction.object.StoreData;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("order")
public class Order {

    @Transient
    public static final String SEQUENCE_NAME = "order_sequence";

    @Id
    private Long id;

    // Buyer
    @NotBlank
    @Field(name = "buyer_id")
    private String buyerId;

    private String description;

    @Field(name = "store_list")
    private List<StoreData> storeList;

    @NotBlank
    @Field(name = "total_price")
    private String totalPrice;

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
