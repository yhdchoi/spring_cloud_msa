package com.yhdc.store_server.transaction.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Document("store")
public class Store {

    @Transient
    public static final String SEQUENCE_NAME = "store_sequence";

    @Id
    private Long id;

    // Seller(Owner)
    @NotBlank
    @Field(name = "seller_id")
    private String sellerId;

    @NotBlank
    private String name;

    private String description;

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
