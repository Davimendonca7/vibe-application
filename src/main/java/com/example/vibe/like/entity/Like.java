package com.example.vibe.like.entity;

import com.example.vibe.account.entity.Account;
import com.example.vibe.comment.entity.Comment;
import com.example.vibe.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.time.OffsetDateTime;
import java.util.UUID;

@Table(
        name ="Like",
        uniqueConstraints = {@UniqueConstraint(
                name = "single_like_on_post_per_account",
                columnNames = {"author_id", "post_id"}
        ), @UniqueConstraint(
                name = "single_like_on_comment_per_account",
                columnNames = {"author_id", "comment_id"}
        )})
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Check(constraints = "(post_id IS NOT NULL AND comment_id IS NULL) or (comment_id IS NOT NULL AND post_id IS NULL)")
public class Like {

    @Id
    @Column(columnDefinition = "CHAR(36)")
    private UUID id =  UUID.randomUUID();
    @ManyToOne
    //@Column(name = "author_id")
    private Account author;
    @ManyToOne
    //@Column(name = "post_id")
    private Post post;
    @ManyToOne
    //@Column(name = "comment_id")
    private Comment comment;
    private OffsetDateTime createdAt;

    @PrePersist
    public void prePersist(){
        createdAt = OffsetDateTime.now();
    }
}
