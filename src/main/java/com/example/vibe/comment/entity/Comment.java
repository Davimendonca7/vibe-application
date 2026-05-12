package com.example.vibe.comment.entity;

import com.example.vibe.account.entity.Account;
import com.example.vibe.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @Column(columnDefinition = "CHAR(36)")
    private UUID id = UUID.randomUUID();
    private String content;
    @ManyToOne
    private Post post;
    @ManyToOne
    private Account author;
    private OffsetDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
    }
}
