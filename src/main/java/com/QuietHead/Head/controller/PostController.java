package com.QuietHead.Head.controller;

import com.QuietHead.Head.domain.Post;
import com.QuietHead.Head.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        log.info("Creating new post");
        Post createdPost = postService.createPost(post);
        log.info("Post created successfully with ID: {}", createdPost.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
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
        Optional<Post> post = postService.getPostById(id);
        return post.map(ResponseEntity::ok)
                 .orElseGet(() -> {
                     log.warn("Post not found with ID: {}", id);
                     return ResponseEntity.notFound().build();
                 });
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, 
                                         @RequestBody Post postDetails) {
        log.info("Updating post with ID: {}", id);
        Post updatedPost = postService.updatePost(id, postDetails);
        log.info("Post updated successfully: {}", id);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        log.info("Deleting post with ID: {}", id);
        postService.deletePost(id);
        log.info("Post deleted successfully: {}", id);
        return ResponseEntity.noContent().build();
    }
}