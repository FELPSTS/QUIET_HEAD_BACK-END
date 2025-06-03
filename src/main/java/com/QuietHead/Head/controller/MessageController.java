package com.QuietHead.Head.controller;

import com.QuietHead.Head.domain.Message;
import com.QuietHead.Head.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // Create a new message
    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message created = messageService.save(message);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<Message>> listMessages() {
        List<Message> messages = messageService.listAll();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        Optional<Message> messageOpt = messageService.findById(id);
        if (messageOpt.isPresent()) {
            return ResponseEntity.ok(messageOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update a message by ID
    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable Long id, @RequestBody Message updatedMessage) {
        Optional<Message> messageOpt = messageService.update(id, updatedMessage);
        if (messageOpt.isPresent()) {
            return ResponseEntity.ok(messageOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a message by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        boolean deleted = messageService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/send/{senderId}/to/{receiverId}")
    public ResponseEntity<Message> sendMessage(
            @PathVariable Long senderId,
            @PathVariable Long receiverId,
            @RequestBody Message message) {

        Optional<Message> sentMessage = messageService.sendMessage(senderId, receiverId, message);
        if (sentMessage.isPresent()) {
            return ResponseEntity.ok(sentMessage.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
