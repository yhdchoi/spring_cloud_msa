package com.yhdc.account_server.data;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class EntityDateAudit implements Serializable {


    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private String createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private String modifiedAt;

    @PrePersist
    public void onPrePersist() {
        this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.modifiedAt = this.createdAt;
    }

    @PreUpdate
    public void onPreUpdate() {
        this.modifiedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
