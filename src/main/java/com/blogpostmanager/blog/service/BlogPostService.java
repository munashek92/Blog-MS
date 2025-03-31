package com.blogpostmanager.blog.service;

import com.blogpostmanager.blog.entity.BlogPost;
import com.blogpostmanager.blog.repository.BlogPostRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BlogPostService {
    private BlogPostRepository blogPostRepository;
    private final Logger logger = LogManager.getLogger(BlogPostService.class);

    public BlogPostService(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    public List<BlogPost> getAllBlogPosts() {
        logger.info("Getting all blog posts");
        return blogPostRepository.findAll();
    }

    public Optional<BlogPost> getBlogPostById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        return blogPostRepository.findById(id);
    }

    public BlogPost createBlogPost(BlogPost blogPost) {
        if (Objects.isNull(blogPost)) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return blogPostRepository.save(blogPost);
    }

    public BlogPost updateBlogPost(Integer id, BlogPost blogPostDetails) {
        if (null == id || null == blogPostDetails) {
            throw new IllegalArgumentException("Blog post id and details cannot be null");
        }

        BlogPost blogPost = blogPostRepository.findById(id).orElseThrow();
        blogPost.setTitle(blogPostDetails.getTitle());
        blogPost.setContent(blogPostDetails.getContent());
        blogPost.setAuthor(blogPostDetails.getAuthor());
        blogPost.setCreatedOn(blogPostDetails.getCreatedOn());
        return blogPostRepository.save(blogPost);
    }

    public void deleteBlogPost(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }

        blogPostRepository.deleteById(id);
    }
}
