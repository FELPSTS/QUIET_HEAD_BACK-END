package com.QuietHead.Head.controller;

import com.QuietHead.Head.domain.Message;
import com.QuietHead.Head.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        log.info("Creating new message");
        Message created = messageService.save(message);
        log.info("Message created successfully with ID: {}", created.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Message>> listMessages() {
        log.debug("Fetching all messages");
        List<Message> messages = messageService.listAll();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        log.debug("Fetching message by ID: {}", id);
        return messageService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(
            @PathVariable Long id,
            @RequestBody Message updatedMessage) {
        log.info("Updating message with ID: {}", id);
        return messageService.update(id, updatedMessage)
                .map(message -> {
                    log.info("Message updated successfully: {}", id);
                    return ResponseEntity.ok(message);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        log.info("Deleting message with ID: {}", id);
        boolean deleted = messageService.delete(id);
        if (deleted) {
            log.info("Message deleted successfully: {}", id);
            return ResponseEntity.noContent().build();
        } else {
            log.warn("Message not found for deletion: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(
            @RequestParam Long senderId,
            @RequestParam Long receiverId,
            @RequestBody Message message) {
        log.info("Sending message from user {} to user {}", senderId, receiverId);
        
        Optional<Message> sentMessage = messageService.sendMessage(senderId, receiverId, message);
        return sentMessage.map(m -> {
                    log.info("Message sent successfully with ID: {}", m.getId());
                    return ResponseEntity.status(HttpStatus.CREATED).body(m);
                })
                .orElse(ResponseEntity.badRequest().build());
    }
}
