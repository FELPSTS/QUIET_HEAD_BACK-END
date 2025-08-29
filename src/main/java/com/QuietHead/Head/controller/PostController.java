package com.QuietHead.Head.controller;

import com.QuietHead.Head.domain.Post;
import com.QuietHead.Head.service.PostService;

import jakarta.el.PropertyNotFoundException;

import com.QuietHead.Head.dto.LikeResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.ProviderNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        log.info("Creating new post");
        try {
            Post createdPost = postService.createPost(post);
            log.info("Post created successfully with ID: {}", createdPost.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
        } catch (IllegalArgumentException e) {
            log.error("Error creating post: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        log.debug("Fetching all posts");
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        log.debug("Fetching post by ID: {}", id);
        try {
            Optional<Post> post = postService.getPostById(id);
            return post.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Error fetching post: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, 
                                         @RequestBody Post postDetails) {
        log.info("Updating post with ID: {}", id);
        try {
            Post updatedPost = postService.updatePost(id, postDetails);
            log.info("Post updated successfully: {}", id);
            return ResponseEntity.ok(updatedPost);
        } catch (IllegalArgumentException | PropertyNotFoundException e) {
            log.error("Error updating post: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        log.info("Deleting post with ID: {}", id);
        try {
            postService.deletePost(id);
            log.info("Post deleted successfully: {}", id);
            return ResponseEntity.noContent().build();
        } catch (ProviderNotFoundException e) {
            log.error("Error deleting post: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> likePost(@PathVariable Long id, 
                                       @RequestParam String userId) {
        log.info("User {} liking post with ID: {}", userId, id);
        try {
            postService.likePost(id, userId);
            return ResponseEntity.ok().build();
        } catch (ProviderNotFoundException e) {
            log.error("Post not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error liking post: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{id}/unlike")
    public ResponseEntity<Void> unlikePost(@PathVariable Long id, 
                                         @RequestParam String userId) {
        log.info("User {} unliking post with ID: {}", userId, id);
        try {
            postService.unlikePost(id, userId);
            return ResponseEntity.ok().build();
        } catch (ProviderNotFoundException e) {
            log.error("Post not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error unliking post: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/like-status")
    public ResponseEntity<LikeResponse> getLikeStatus(@PathVariable Long id, 
                                                    @RequestParam String userId) {
        log.debug("Checking like status for user {} on post ID: {}", userId, id);
        try {
            int likeCount = postService.getLikeCount(id);
            boolean hasLiked = postService.hasUserLikedPost(id, userId);
            Set<String> likedBy = postService.getLikedBy(id);
            
            LikeResponse response = new LikeResponse(likeCount, hasLiked, likedBy);
            return ResponseEntity.ok(response);
        } catch (ProviderNotFoundException e) {
            log.error("Post not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error getting like status: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/likes")
    public ResponseEntity<Integer> getLikeCount(@PathVariable Long id) {
        log.debug("Getting like count for post ID: {}", id);
        try {
            int likeCount = postService.getLikeCount(id);
            return ResponseEntity.ok(likeCount);
        } catch (ProviderNotFoundException e) {
            log.error("Post not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error getting like count: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/liked-by")
    public ResponseEntity<Set<String>> getLikedBy(@PathVariable Long id) {
        log.debug("Getting users who liked post ID: {}", id);
        try {
            Set<String> likedBy = postService.getLikedBy(id);
            return ResponseEntity.ok(likedBy);
        } catch (ProviderNotFoundException e) {
            log.error("Post not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error getting liked by list: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}