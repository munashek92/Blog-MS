package com.blogpostmanager.blog.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.blogpostmanager.blog.entity.BlogPost;
import com.blogpostmanager.blog.service.BlogPostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class BlogPostControllerTest {

    @Mock
    private BlogPostService blogPostService;
    @Mock
    private BlogPostController blogPostController;

    @BeforeEach
    void setUp() {
        blogPostService = mock(BlogPostService.class);
        blogPostController = new BlogPostController(blogPostService);
    }

    @Test
    void getAllBlogPosts_ReturnsListOfBlogPosts_HappyFlow() {
        List<BlogPost> blogPosts = Arrays.asList(new BlogPost(), new BlogPost());
        when(blogPostService.getAllBlogPosts()).thenReturn(blogPosts);

        ResponseEntity<List<BlogPost>> response = blogPostController.getAllBlogPosts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(blogPosts, response.getBody());
    }

    @Test
    void getAllBlogPosts_HandlesException() {
        when(blogPostService.getAllBlogPosts()).thenThrow(new RuntimeException());

        ResponseEntity<List<BlogPost>> response = blogPostController.getAllBlogPosts();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getBlogPostById_ReturnsBlogPost_HappyFlow() {
        BlogPost blogPost = new BlogPost();
        when(blogPostService.getBlogPostById(1)).thenReturn(Optional.of(blogPost));

        ResponseEntity<BlogPost> response = blogPostController.getBlogPostById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(blogPost, response.getBody());
    }

    @Test
    void getBlogPostById_HandlesException() {
        when(blogPostService.getBlogPostById(1)).thenThrow(new RuntimeException());

        ResponseEntity<BlogPost> response = blogPostController.getBlogPostById(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void createBlogPost_CreatesAndReturnsBlogPost() {
        BlogPost blogPost = new BlogPost();
        when(blogPostService.createBlogPost(blogPost)).thenReturn(blogPost);

        ResponseEntity<BlogPost> response = blogPostController.createBlogPost(blogPost);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(blogPost, response.getBody());
    }

    @Test
    void createBlogPost_ThrowsException_WhenBlogPostIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> blogPostController.createBlogPost(null));
        assertEquals("Blog post cannot be null", exception.getMessage());
    }

    @Test
    void createBlogPost_HandlesException() {
        BlogPost blogPost = new BlogPost();
        when(blogPostService.createBlogPost(blogPost)).thenThrow(new RuntimeException());

        ResponseEntity<BlogPost> response = blogPostController.createBlogPost(blogPost);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void updateBlogPost_UpdatesAndReturnsBlogPost() {
        BlogPost blogPostDetails = new BlogPost();
        BlogPost updatedBlogPost = new BlogPost();
        when(blogPostService.updateBlogPost(1, blogPostDetails)).thenReturn(updatedBlogPost);

        ResponseEntity<BlogPost> response = blogPostController.updateBlogPost(1, blogPostDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedBlogPost, response.getBody());
    }

    @Test
    void updateBlogPost_ThrowsException_WhenBlogPostDetailsAreNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> blogPostController.updateBlogPost(1, null));
        assertEquals("Blog post id and details cannot be null", exception.getMessage());
    }

    @Test
    void updateBlogPost_HandlesException() {
        BlogPost blogPostDetails = new BlogPost();
        when(blogPostService.updateBlogPost(1, blogPostDetails)).thenThrow(new RuntimeException());

        ResponseEntity<BlogPost> response = blogPostController.updateBlogPost(1, blogPostDetails);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void deleteBlogPost_DeletesBlogPost_HappyFlow() {
        ResponseEntity<Void> response = blogPostController.deleteBlogPost(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(blogPostService, times(1)).deleteBlogPost(1);
    }

    @Test
    void deleteBlogPost_ThrowsException_WhenIdIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> blogPostController.deleteBlogPost(null));

        assertEquals("Blog post id cannot be null", exception.getMessage());
    }

    @Test
    void deleteBlogPost_HandlesException() {
        doThrow(new RuntimeException()).when(blogPostService).deleteBlogPost(1);

        ResponseEntity<Void> response = blogPostController.deleteBlogPost(1);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
