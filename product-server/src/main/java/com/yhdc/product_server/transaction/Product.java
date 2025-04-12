package com.yhdc.product_server.transaction;

import com.yhdc.product_server.type.ProductStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "product")
@Entity
public class Product extends EntityDateAudit {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false, unique = true)
    private UUID id;

    // Seller
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    // Seller
    @Column(name = "store_id", nullable = false)
    private UUID storeId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "text", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private String price;

    @Column(name = "status", nullable = false)
    private ProductStatus status;

    @Column(name = "stock", nullable = false)
    private String stock;

}
