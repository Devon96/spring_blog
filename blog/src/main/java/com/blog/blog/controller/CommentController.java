package com.blog.blog.controller;


import com.blog.blog.model.Comment;
import com.blog.blog.model.Post;
import com.blog.blog.service.CommentService;
import com.blog.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/comment")
public class CommentController {

    PostService postService;
    CommentService commentService;

    @Autowired
    public CommentController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @PostMapping("/add/{id}")
    public String addComment(@PathVariable long id, Comment comment){

        comment.setPost(postService.findById(id));
        commentService.save(comment);

        return "redirect:/post/" + id;
    }

    @GetMapping("/delete/{postid}/{id}")
    public String deletePost(@PathVariable long postid, @PathVariable long id){
        commentService.deleteById(id);
        return "redirect:/post/" + postid;
    }




}
