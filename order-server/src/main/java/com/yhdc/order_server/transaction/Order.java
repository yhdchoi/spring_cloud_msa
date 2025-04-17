package com.yhdc.order_server.transaction;

import com.yhdc.order_server.object.StoreData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document("order")
public class Order {

    @Id
    private String id;

    // Seller
    private String userId;
    private List<StoreData> storeList;

    private String price;
    private String status;

    @CreatedDate
    private OffsetDateTime createdAt;

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
