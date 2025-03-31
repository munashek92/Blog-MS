package com.blogpostmanager.blog.service;

import com.blogpostmanager.blog.entity.BlogPost;
import com.blogpostmanager.blog.repository.BlogPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BlogPostServiceTest {

    @Mock
    private BlogPostRepository blogPostRepository;

    private BlogPostService blogPostService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        blogPostService = new BlogPostService(blogPostRepository);
    }

    @Test
    void getAllBlogPosts_ReturnsListOfBlogPosts() {
        when(blogPostRepository.findAll()).thenReturn(List.of(new BlogPost(), new BlogPost()));

        assertNotNull(blogPostService.getAllBlogPosts());
        assertEquals(2, blogPostService.getAllBlogPosts().size());
    }

    @Test
    void getBlogPostById_ReturnsBlogPost() {
        BlogPost blogPost = new BlogPost();
        when(blogPostRepository.findById(1)).thenReturn(Optional.of(blogPost));

        assertEquals(blogPost, blogPostService.getBlogPostById(1).orElse(null));
    }

    @Test
    void getBlogPostById_ThrowsException_WhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> blogPostService.getBlogPostById(null));
    }

    @Test
    void createBlogPost_SavesAndReturnsBlogPost() {
        BlogPost blogPost = new BlogPost();
        when(blogPostRepository.save(blogPost)).thenReturn(blogPost);

        assertEquals(blogPost, blogPostService.createBlogPost(blogPost));
    }

    @Test
    void createBlogPost_ThrowsException_WhenBlogPostIsNull() {
        assertThrows(IllegalArgumentException.class, () -> blogPostService.createBlogPost(null));
    }

    @Test
    void updateBlogPost_UpdatesAndReturnsBlogPost() {
        BlogPost blogPost = new BlogPost();
        BlogPost blogPostDetails = new BlogPost();
        when(blogPostRepository.findById(1)).thenReturn(Optional.of(blogPost));
        when(blogPostRepository.save(blogPost)).thenReturn(blogPost);

        assertEquals(blogPost, blogPostService.updateBlogPost(1, blogPostDetails));
    }

    @Test
    void updateBlogPost_ThrowsException_WhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> blogPostService.updateBlogPost(null, new BlogPost()));
    }

    @Test
    void updateBlogPost_ThrowsException_WhenBlogPostDetailsAreNull() {
        assertThrows(IllegalArgumentException.class, () -> blogPostService.updateBlogPost(1, null));
    }

    @Test
    void deleteBlogPost_DeletesBlogPost() {
        doNothing().when(blogPostRepository).deleteById(1);

        blogPostService.deleteBlogPost(1);

        verify(blogPostRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteBlogPost_ThrowsException_WhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> blogPostService.deleteBlogPost(null));
    }
}
