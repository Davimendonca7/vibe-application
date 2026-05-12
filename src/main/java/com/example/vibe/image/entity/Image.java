package com.example.vibe.image.entity;

import com.example.vibe.comment.entity.Comment;
import com.example.vibe.post.entity.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Check(constraints = "(post_id IS NOT NULL AND comment_id IS NULL) or (comment_id IS NOT NULL AND post_id IS NULL)")
public class Image {

    @Id
    @Column(columnDefinition = "CHAR(36)")
    private UUID id = UUID.randomUUID();
    private String urlImage;
    @ManyToOne
    private Post post;
    @ManyToOne
    private Comment comment;
    private OffsetDateTime createdAt;

    @PrePersist
    protected void prePersist() {
        this.createdAt = OffsetDateTime.now();
    }
}
