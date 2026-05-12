package com.example.vibe.account.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @Column(columnDefinition = "CHAR(36)")
    private UUID id = UUID.randomUUID();
    private String password;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String username;
    private String biography;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void prePersist() {
        createdAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
