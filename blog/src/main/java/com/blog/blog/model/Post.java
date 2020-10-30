package com.blog.blog.model;


import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
public class Post extends AuditableEntity<User> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min = 3, message = "A címnek minimum 3 karakternek kell lennie.")
    @NotNull
    private String title;

    @Size(min = 8, message = "A tartalomnak minimum 8 karakternek kell lennie.")
    @NotNull
    @Lob
    private String content;


    @ManyToOne
    @Valid
    private Category category;

    @OneToMany
    private List<Comment> comments;

    @OneToMany
    private List<PostRating> postRatings;

    @Transient
    private int ratingCount;

    @Transient
    private boolean userLiked;

    @Transient
    private boolean userDisliked;


}