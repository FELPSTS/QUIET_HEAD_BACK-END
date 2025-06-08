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

    @Autowired
    private ConversationService conversationService;

    @PostMapping
    public ResponseEntity<Conversation> createConversation(@RequestBody List<Long> clientIds) {
        Conversation conversation = conversationService.createConversation(clientIds);
        return ResponseEntity.ok(conversation);
    }

    @PostMapping("/{conversationId}/send")
    public ResponseEntity<?> sendMessage(
            @PathVariable Long conversationId,
            @RequestParam Long senderId,
            @RequestParam String text
    ) {
        Optional<Message> messageOpt = conversationService.sendMessage(conversationId, senderId, text);
        return messageOpt.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getConversation(@PathVariable Long id) {
        Optional<Conversation> conv = conversationService.getConversation(id);
        return conv.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{conversationId}/attach-messages")
    public ResponseEntity<Void> attachMessages(
            @PathVariable Long conversationId,
            @RequestBody List<Long> messageIds) {

        boolean success = conversationService.attachExistingMessagesToConversation(conversationId, messageIds);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Conversation>> getAllConversations() {
        return ResponseEntity.ok(conversationService.getAllConversations());
    }
}
