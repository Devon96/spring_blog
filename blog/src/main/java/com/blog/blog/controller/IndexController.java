package com.blog.blog.controller;


import com.blog.blog.service.CategoryService;
import com.blog.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

    PostService postService;
    CategoryService categoryService;

    @Autowired
    public IndexController(PostService postService, CategoryService categoryService) {
        this.postService = postService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String posts(Model model){
        model.addAttribute("title", "Bejegyzések");
        model.addAttribute("posts", postService.findAll());
        model.addAttribute("categories",categoryService.findAll());
        return "index";
    }

    @PostMapping("/")
    public String postsPost(Model model){
        model.addAttribute("title", "Bejegyzések");
        model.addAttribute("posts", postService.findAll());
        model.addAttribute("categories",categoryService.findAll());
        return "index";
    }


}
