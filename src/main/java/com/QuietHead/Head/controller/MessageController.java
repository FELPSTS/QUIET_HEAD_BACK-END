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

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message created = messageService.save(message);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Message>> listMessages() {
        List<Message> messages = messageService.listAll();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        return messageService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable Long id,
                                                 @RequestBody Message updatedMessage) {
        return messageService.update(id, updatedMessage)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        boolean deleted = messageService.delete(id);
        return deleted ? ResponseEntity.noContent().build()
                       : ResponseEntity.notFound().build();
    }

    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(
            @RequestParam Long senderId,
            @RequestParam Long receiverId,
            @RequestBody Message message) {

        Optional<Message> sentMessage = messageService.sendMessage(senderId, receiverId, message);
        return sentMessage.map(m -> ResponseEntity.status(201).body(m))
                          .orElse(ResponseEntity.badRequest().build());
    }
}
