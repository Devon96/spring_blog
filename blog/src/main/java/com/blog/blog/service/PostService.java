package com.blog.blog.service;

import com.blog.blog.model.Post;
import com.blog.blog.model.PostRating;
import com.blog.blog.model.User;
import com.blog.blog.repository.PostRatingRepository;
import com.blog.blog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private PostRepository postRepository;
    private CategoryService categoryService;
    private UserService userService;
    private PostRatingService postRatingService;

    @Autowired
    public PostService(PostRepository postRepository, CategoryService categoryService, UserService userService, PostRatingService postRatingService) {
        this.postRepository = postRepository;
        this.categoryService = categoryService;
        this.userService = userService;
        this.postRatingService = postRatingService;
    }

    public void save(Post post){
        String str = post.getCategory().getName();
        str = str.strip().toUpperCase();
        if(str.length() > 0 && str.substring(str.length()-1).equals(",")){
            str = str.substring(0, str.length() - 1);
        }
        post.getCategory().setName(str);

        categoryService.save(post.getCategory());
        postRepository.save(post);
    }

    public List<Post> findAll() {

        List<Post> posts = postRepository.findAll();

        for (Post post : posts){
            post.setRatingCount(postRatingService.getPostRatingValue(post));
        }
        return posts;
    }


    public void deleteById(long id) {
        postRepository.deleteById(id);
    }

    public Post findById(long id){
        Post post = postRepository.findById(id).orElse(null);
        if(post != null){
            User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            post.setRatingCount(postRatingService.getPostRatingValue(post));

            if (user != null){
                PostRating postRating = postRatingService.findByPostAndUser(post, user);
                if(postRating == null){
                    post.setUserLiked(false);
                    post.setUserDisliked(false);
                }else{
                    if(postRating.isRating()){
                        post.setUserLiked(true);
                        post.setUserDisliked(false);
                    }else{
                        post.setUserLiked(false);
                        post.setUserDisliked(true);
                    }
                }
            }
        }
        return post;
    }

}
