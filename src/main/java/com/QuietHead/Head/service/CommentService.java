package com.QuietHead.Head.service;

import com.QuietHead.Head.domain.Comment;
import com.QuietHead.Head.domain.Post;
import com.QuietHead.Head.domain.Client;
import com.QuietHead.Head.repository.CommentRepository;
import com.QuietHead.Head.repository.PostRepository;
import com.QuietHead.Head.repository.ClientRepository;
import com.QuietHead.Head.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public Comment createComment(Long postId, Comment comment) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post não encontrado com ID: " + postId));
        
        if (comment.getContent() == null || comment.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Conteúdo do comentário não pode ser vazio");
        }
        
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        comment.setPost(post);
        
        Comment savedComment = commentRepository.save(comment);
        
        createCommentRelationship(postId, savedComment.getId());
        
        log.info("Comment created successfully with ID: {} for post ID: {}", savedComment.getId(), postId);
        return savedComment;
    }

    @Transactional
    public Comment createCommentWithAuthor(Long postId, Long authorId, Comment comment) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post não encontrado com ID: " + postId));
        
        Client author = clientRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author não encontrado com ID: " + authorId));
        
        if (comment.getContent() == null || comment.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Conteúdo do comentário não pode ser vazio");
        }
        
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        comment.setPost(post);
        comment.setAuthor(author);
        
        Comment savedComment = commentRepository.save(comment);
        
        createCommentRelationship(postId, savedComment.getId());
        
        log.info("Comment created successfully with ID: {} for post ID: {} by author ID: {}", 
                savedComment.getId(), postId, authorId);
        return savedComment;
    }

    @Transactional
    public void createCommentRelationship(Long postId, Long commentId) {
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        log.debug("Fetching comments for post ID: {}", postId);
        return commentRepository.findByPostId(postId);
    }

    public List<Comment> getCommentsByPostIdWithRelationship(Long postId) {
        if (!postRepository.existsById(postId)) {
        }
        
        log.debug("Fetching comments with relationship for post ID: {}", postId);
        return null;
    }

    public List<Comment> getCommentsByAuthorId(Long authorId) {
        log.debug("Fetching comments by author ID: {}", authorId);
        return commentRepository.findByAuthorId(authorId);
    }

    public Comment getCommentById(Long commentId) {
        log.debug("Fetching comment by ID: {}", commentId);
        return null;
    }

    public Comment getCommentById(Long postId, Long commentId) {
        log.debug("Fetching comment ID: {} for post ID: {}", commentId, postId);
        Comment comment = getCommentById(commentId);
        
        if (!comment.getPost().getId().equals(postId)) {
        }
        
        return comment;
    }

    @Transactional
    public Comment updateComment(Long commentId, Comment commentDetails) {
        Comment comment = getCommentById(commentId);
        
        comment.setContent(commentDetails.getContent());
        comment.setUpdatedAt(LocalDateTime.now());
        
        Comment updatedComment = commentRepository.save(comment);
        log.info("Comment updated successfully: {}", commentId);
        return updatedComment;
    }

    @Transactional
    public Comment updateComment(Long postId, Long commentId, Comment commentDetails) {
        Comment comment = getCommentById(postId, commentId);
        
        comment.setContent(commentDetails.getContent());
        comment.setUpdatedAt(LocalDateTime.now());
        
        Comment updatedComment = commentRepository.save(comment);
        log.info("Comment updated successfully: {}", commentId);
        return updatedComment;
    }

    @Transactional
    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
        }
        
        commentRepository.deleteById(commentId);
        log.info("Comment deleted successfully: {}", commentId);
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        Comment comment = getCommentById(postId, commentId);
        commentRepository.delete(comment);
        log.info("Comment deleted successfully: {}", commentId);
    }

    @Transactional
    public void deleteCommentsByPostId(Long postId) {
        log.debug("Deleting all comments for post ID: {}", postId);
        commentRepository.deleteByPostId(postId);
        log.info("All comments deleted for post ID: {}", postId);
    }
}