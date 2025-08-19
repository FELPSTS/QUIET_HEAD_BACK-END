package com.QuietHead.Head.controller;

import com.QuietHead.Head.domain.Conversation;
import com.QuietHead.Head.domain.Message;
import com.QuietHead.Head.service.ConversationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/conversations")
@RequiredArgsConstructor
public class ConversationController {

    private final ConversationService conversationService;

    @PostMapping
    public ResponseEntity<Conversation> createConversation(@Valid @RequestBody List<Long> clientIds) {
        log.info("Creating conversation for client IDs: {}", clientIds);
        
        Conversation conversation = conversationService.createConversation(clientIds);
        
        log.info("Conversation created successfully with ID: {}", conversation.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(conversation);
    }

    @PostMapping("/{conversationId}/messages")
    public ResponseEntity<Message> sendMessage(
            @PathVariable Long conversationId,
            @RequestParam Long senderId,
            @RequestParam String text) {
        
        log.info("Sending message to conversation ID: {}, sender ID: {}", conversationId, senderId);
        
        Optional<Message> messageOpt = conversationService.sendMessage(conversationId, senderId, text);
        return messageOpt.map(message -> {
                    log.info("Message sent successfully to conversation ID: {}", conversationId);
                    return ResponseEntity.ok(message);
                })
                .orElseGet(() -> {
                    log.warn("Failed to send message to conversation ID: {}", conversationId);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping("/{conversationId}")
    public ResponseEntity<Conversation> getConversation(@PathVariable Long conversationId) {
        log.debug("Fetching conversation by ID: {}", conversationId);
        
        Optional<Conversation> conv = conversationService.getConversation(conversationId);
        return conv.map(conversation -> {
                    log.debug("Conversation found: {}", conversationId);
                    return ResponseEntity.ok(conversation);
                })
                .orElseGet(() -> {
                    log.warn("Conversation not found with ID: {}", conversationId);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{conversationId}/messages")
    public ResponseEntity<Void> attachMessages(
            @PathVariable Long conversationId,
            @Valid @RequestBody List<Long> messageIds) {
        
        log.info("Attaching {} messages to conversation ID: {}", messageIds.size(), conversationId);
        
        boolean success = conversationService.attachExistingMessagesToConversation(conversationId, messageIds);
        if (success) {
            log.info("Messages attached successfully to conversation ID: {}", conversationId);
            return ResponseEntity.ok().build();
        } else {
            log.warn("Failed to attach messages to conversation ID: {}", conversationId);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Conversation>> getAllConversations() {
        log.debug("Fetching all conversations");
        List<Conversation> conversations = conversationService.getAllConversations();
        log.debug("Found {} conversations", conversations.size());
        return ResponseEntity.ok(conversations);
    }
}
