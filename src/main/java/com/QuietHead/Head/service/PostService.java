package com.QuietHead.Head.service;

import com.QuietHead.Head.domain.*;
import com.QuietHead.Head.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final ClientRepository clientRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Post createPost(Post post) {
        if (post == null || post.getAuthor() == null) {
            throw new IllegalArgumentException("Post and author must not be null");
        }

        String email = post.getAuthor().getEmail();
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Author email must be provided");
        }

        Client author = clientRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Author not found with email: " + email));

        validatePostContent(post.getContent());

        LocalDateTime now = LocalDateTime.now();
        post.setAuthor(author);
        post.setCreatedAt(now);
        post.setUpdatedAt(now);
        post.setLikeCount(0);

        return postRepository.save(post);
    }

    private void validatePostContent(String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Post content must not be empty");
        }
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Transactional
    public Post updatePost(Long id, Post postUpdates) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + id));

        if (postUpdates.getContent() != null) {
            validatePostContent(postUpdates.getContent());
            existingPost.setContent(postUpdates.getContent());
            existingPost.setUpdatedAt(LocalDateTime.now());
        }

        return postRepository.save(existingPost);
    }

    @Transactional
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new IllegalArgumentException("Post not found with id: " + id);
        }
        postRepository.deleteById(id);
    }

    @Transactional
    public Comment addComment(Long postId, Comment comment) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with id: " + postId));

        if (comment.getAuthor() == null || comment.getContent() == null || comment.getContent().isBlank()) {
            throw new IllegalArgumentException("Comment must have an author and non-empty content");
        }

        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsForPost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new IllegalArgumentException("Post not found with id: " + postId);
        }
        return commentRepository.findByPostId(postId);
    }
}
