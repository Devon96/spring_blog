package com.blog.blog.model;


import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity
public class Comment extends AuditableEntity<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Lob
    private String text;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Post post;

    @OneToMany
    private List<CommentRating> commentRatings;

    @Transient
    private int ratingCount;

    @Transient
    private boolean userLiked;

    @Transient
    private boolean userDisliked;

}
