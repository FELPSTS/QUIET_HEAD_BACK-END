package com.QuietHead.Head.controller;

import com.QuietHead.Head.domain.Comment;
import com.QuietHead.Head.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/posts/{postId}/comments") 
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody Comment comment) {
        log.info("Creating comment for post ID: {}", postId);
        Comment createdComment = commentService.createComment(postId, comment);
        log.info("Comment created successfully with ID: {}", createdComment.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @PostMapping("/author/{authorId}")
    public ResponseEntity<Comment> createCommentWithAuthor(
            @PathVariable Long postId,
            @PathVariable Long authorId,
            @Valid @RequestBody Comment comment) {
        log.info("Creating comment for post ID: {} by author ID: {}", postId, authorId);
        Comment createdComment = commentService.createCommentWithAuthor(postId, authorId, comment);
        log.info("Comment created successfully with ID: {}", createdComment.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        log.debug("Fetching comments for post ID: {}", postId);
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentById(
            @PathVariable Long postId,
            @PathVariable Long commentId) {
        log.debug("Fetching comment ID: {} for post ID: {}", commentId, postId);
        Comment comment = commentService.getCommentById(postId, commentId);
        return ResponseEntity.ok(comment);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @Valid @RequestBody Comment commentDetails) {
        log.info("Updating comment ID: {} for post ID: {}", commentId, postId);
        Comment updatedComment = commentService.updateComment(postId, commentId, commentDetails);
        log.info("Comment updated successfully: {}", commentId);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId) {
        log.info("Deleting comment ID: {} from post ID: {}", commentId, postId);
        commentService.deleteComment(postId, commentId);
        log.info("Comment deleted successfully: {}", commentId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllCommentsByPostId(@PathVariable Long postId) {
        log.info("Deleting all comments for post ID: {}", postId);
        commentService.deleteCommentsByPostId(postId);
        log.info("All comments deleted for post ID: {}", postId);
        return ResponseEntity.noContent().build();
    }
}