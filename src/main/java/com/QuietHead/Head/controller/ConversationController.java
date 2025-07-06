package com.QuietHead.Head.controller;

import com.QuietHead.Head.domain.Conversation;
import com.QuietHead.Head.domain.Message;
import com.QuietHead.Head.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    @Autowired
    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping
    public ResponseEntity<Conversation> createConversation(@RequestBody List<Long> clientIds) {
        Conversation conversation = conversationService.createConversation(clientIds);
        return ResponseEntity.status(201).body(conversation);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> sendMessage(
            @PathVariable Long conversationId,
            @RequestParam Long senderId,
            @RequestParam String text) {

        Optional<Message> messageOpt = conversationService.sendMessage(conversationId, senderId, text);
        return messageOpt.map(ResponseEntity::ok)
                         .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{Id}")
    public ResponseEntity<Conversation> getConversation(@PathVariable Long conversationId) {
        Optional<Conversation> conv = conversationService.getConversation(conversationId);
        return conv.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{conversation}/messages")
    public ResponseEntity<Void> attachMessages(
            @PathVariable Long conversationId,
            @RequestBody List<Long> messageIds) {

        boolean success = conversationService.attachExistingMessagesToConversation(conversationId, messageIds);
        return success ? ResponseEntity.ok().build()
                       : ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<Conversation>> getAllConversations() {
        return ResponseEntity.ok(conversationService.getAllConversations());
    }
}
