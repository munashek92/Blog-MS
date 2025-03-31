package com.blogpostmanager.blog.controller;

import com.blogpostmanager.blog.entity.BlogPost;
import com.blogpostmanager.blog.service.BlogPostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/blogposts")
public class BlogPostController {

    private final BlogPostService blogPostService;

    private static final Logger logger = LogManager.getLogger(BlogPostController.class);

    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping
    public ResponseEntity<List<BlogPost>> getAllBlogPosts() {
        logger.info("Inside getAllBlogPosts method");
        ResponseEntity<List<BlogPost>> response;

        try {
            List<BlogPost> blogPosts = blogPostService.getAllBlogPosts();
            response = ResponseEntity.ok(blogPosts);
        } catch (Exception e) {
            logger.error("Error fetching all blog posts", e);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> getBlogPostById(@PathVariable Integer id) {
        logger.info("Inside getBlogPostById method, the id is: {}", id);
        ResponseEntity<BlogPost> response;

        if (null == id) {
            throw new IllegalArgumentException("Blog post cannot be null");
        }
        try {
            BlogPost blogPost = blogPostService.getBlogPostById(id).orElseThrow();
            response = ResponseEntity.ok(blogPost);
        } catch (Exception e) {
            logger.error("Error fetching blog post by id {}",id, e);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<BlogPost> createBlogPost(@RequestBody BlogPost blogPost) {
        logger.info("Inside createBlogPost method, the blog post is: {}", blogPost);
        ResponseEntity<BlogPost> response;

        if (Objects.isNull(blogPost)) {
            throw new IllegalArgumentException("Blog post cannot be null");
        }

        try {
            blogPost = blogPostService.createBlogPost(blogPost);
            response = ResponseEntity.ok(blogPost);
        } catch (Exception e) {
            logger.error("Error creating blog post", e);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPost> updateBlogPost(@PathVariable Integer id, @RequestBody BlogPost blogPostDetails) {
        logger.info("Inside updateBlogPost method, the id is: {} and the blog post details are: {}", id, blogPostDetails);
        ResponseEntity<BlogPost> response;

        if (null == id || null == blogPostDetails) {
            throw new IllegalArgumentException("Blog post id and details cannot be null");
        }
        try {
            BlogPost updatedBlogPost = blogPostService.updateBlogPost(id, blogPostDetails);
            response = ResponseEntity.ok(updatedBlogPost);
        } catch (Exception e) {
            logger.error("Error updating blog post", e);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlogPost(@PathVariable Integer id) {
        logger.info("Inside deleteBlogPost method, the id is: {}", id);
        ResponseEntity<Void> response;

        if (null == id) {
            throw new IllegalArgumentException("Blog post id cannot be null");
        }
        try {
            blogPostService.deleteBlogPost(id);
            response = ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting blog post with id: {}", id, e);
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return response;
    }
}
