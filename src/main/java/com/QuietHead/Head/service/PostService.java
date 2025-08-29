package com.QuietHead.Head.service;

import com.QuietHead.Head.domain.Client;
import com.QuietHead.Head.domain.Comment;
import com.QuietHead.Head.domain.Post;
import com.QuietHead.Head.repository.ClientRepository;
import com.QuietHead.Head.repository.CommentRepository;
import com.QuietHead.Head.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final ClientRepository clientRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Post createPost(Post post) {
        log.info("Creating new post for author email: {}", 
                post.getAuthor() != null ? post.getAuthor().getEmail() : "null");
        
        if (post == null || post.getAuthor() == null) {
            log.error("Post or author cannot be null");
            throw new IllegalArgumentException("Post and author must not be null");
        }

        String email = post.getAuthor().getEmail();
        if (email == null || email.isBlank()) {
            log.error("Author email must be provided");
            throw new IllegalArgumentException("Author email must be provided");
        }

        Client author = clientRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("Author not found with email: {}", email);
                    return new IllegalArgumentException("Author not found with email: " + email);
                });

        validatePostContent(post.getContent());

        LocalDateTime now = LocalDateTime.now();
        post.setAuthor(author);
        post.setCreatedAt(now);
        post.setUpdatedAt(now);
        post.setLikeCount(0);

        Post savedPost = postRepository.save(post);
        log.info("Post created successfully with ID: {}", savedPost.getId());
        return savedPost;
    }

    private void validatePostContent(String content) {
        if (content == null || content.isBlank()) {
            log.error("Post content cannot be empty");
            throw new IllegalArgumentException("Post content must not be empty");
        }
    }

    public Optional<Post> getPostById(Long id) {
        log.debug("Fetching post by ID: {}", id);
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            log.debug("Post found with ID: {}", id);
        } else {
            log.debug("Post not found with ID: {}", id);
        }
        return post;
    }

    public List<Post> getAllPosts() {
        log.debug("Fetching all posts");
        List<Post> posts = postRepository.findAll();
        log.debug("Found {} posts", posts.size());
        return posts;
    }

    @Transactional
    public Post updatePost(Long id, Post postUpdates) {
        log.info("Updating post with ID: {}", id);
        
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Post not found for update with ID: {}", id);
                    return new IllegalArgumentException("Post not found with id: " + id);
                });

        if (postUpdates.getContent() != null) {
            validatePostContent(postUpdates.getContent());
            existingPost.setContent(postUpdates.getContent());
            existingPost.setUpdatedAt(LocalDateTime.now());
            log.info("Post content updated for ID: {}", id);
        }

        Post updatedPost = postRepository.save(existingPost);
        log.info("Post updated successfully with ID: {}", id);
        return updatedPost;
    }

    @Transactional
    public void deletePost(Long id) {
        log.info("Deleting post with ID: {}", id);
        
        if (!postRepository.existsById(id)) {
            log.error("Post not found for deletion with ID: {}", id);
            throw new IllegalArgumentException("Post not found with id: " + id);
        }
        
        postRepository.deleteById(id);
        log.info("Post deleted successfully with ID: {}", id);
    }

    @Transactional
    public Comment addComment(Long postId, Comment comment) {
        log.info("Adding comment to post ID: {}", postId);
        
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> {
                    log.error("Post not found for comment with ID: {}", postId);
                    return new IllegalArgumentException("Post not found with id: " + postId);
                });

        if (comment.getAuthor() == null || comment.getContent() == null || comment.getContent().isBlank()) {
            log.error("Comment must have author and non-empty content");
            throw new IllegalArgumentException("Comment must have an author and non-empty content");
        }

        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());
        
        Comment savedComment = commentRepository.save(comment);
        log.info("Comment added successfully with ID: {} to post ID: {}", savedComment.getId(), postId);
        return savedComment;
    }

    public List<Comment> getCommentsForPost(Long postId) {
        log.debug("Fetching comments for post ID: {}", postId);
        
        if (!postRepository.existsById(postId)) {
            log.error("Post not found for comments with ID: {}", postId);
            throw new IllegalArgumentException("Post not found with id: " + postId);
        }
        
        List<Comment> comments = commentRepository.findByPostId(postId);
        log.debug("Found {} comments for post ID: {}", comments.size(), postId);
        return comments;
    }

        @Transactional
    public void likePost(Long postId, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow();
        
        if (post.getLikedBy() == null) {
            post.setLikedBy(new HashSet<>());
        }
        
        // CORRETO: Verifica se o usuário já curtiu
        if (!post.getLikedBy().contains(userId)) {
            post.incrementLikes();
            post.getLikedBy().add(userId); // CORRETO: Add String ao Set<String>
            postRepository.save(post);
            log.info("User {} liked post ID: {}", userId, postId);
        }
    }

    @Transactional
    public void unlikePost(Long postId, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow();
        
        // CORRETO: Verifica e remove
        if (post.getLikedBy() != null && post.getLikedBy().contains(userId)) {
            post.decrementLikes();
            post.getLikedBy().remove(userId); // CORRETO: Remove String do Set<String>
            postRepository.save(post);
            log.info("User {} unliked post ID: {}", userId, postId);
        }
    }

    public boolean hasUserLikedPost(Long postId, String userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow();
        
        return post.getLikedBy() != null && post.getLikedBy().contains(userId);
    }

    public int getLikeCount(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow();
        
        return post.getLikeCount() != null ? post.getLikeCount() : 0;
    }

    public Set<String> getLikedBy(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow();

        return post.getLikedBy() != null ? post.getLikedBy() : new HashSet<>();
    }
}