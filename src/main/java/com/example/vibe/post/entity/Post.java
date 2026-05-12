package com.example.vibe.post.entity;

import com.example.vibe.account.entity.Account;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    @Column(columnDefinition = "CHAR(36)")
    private UUID id =  UUID.randomUUID();
    private String content;
    @ManyToOne
    private Account author;
    private OffsetDateTime createdAt;

    @PrePersist
    protected void prePersist(){
        createdAt = OffsetDateTime.now();
    }
}
