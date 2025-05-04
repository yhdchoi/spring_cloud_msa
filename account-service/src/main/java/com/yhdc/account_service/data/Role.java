package com.yhdc.account_service.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "role")
@Entity
public class Role {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.AUTO)
    @Column(name = "userId",
            columnDefinition = "BINARY(16)",
            updatable = false, nullable = false, unique = true)
    private UUID id;

    // ADMIN MANAGER SELLER BUYER
    @Column(name = "role_name", length = 100, nullable = false)
    private String roleName;

    @Column(name = "role_type", length = 100, nullable = false)
    private RoleType roleType;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.DETACH})
    @JoinTable(name = "user_role"
            , joinColumns = @JoinColumn(name = "role_id")
            , inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> userSet;


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
