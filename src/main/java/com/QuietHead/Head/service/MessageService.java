package com.QuietHead.Head.service;

import com.QuietHead.Head.domain.Client;
import com.QuietHead.Head.domain.Message;
import com.QuietHead.Head.repository.ClientRepository;
import com.QuietHead.Head.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MessageService {

    private final MessageRepository messageRepository;
    private final ClientRepository clientRepository;

    public Optional<Message> sendMessage(Long senderId, Long receiverId, Message message) {
        log.info("Sending message from sender ID: {} to receiver ID: {}", senderId, receiverId);
        
        Optional<Client> senderOpt = clientRepository.findById(senderId);
        Optional<Client> receiverOpt = clientRepository.findById(receiverId);

        if (senderOpt.isPresent() && receiverOpt.isPresent()) {
            message.setText(message.getText());
            message.setDataHora(LocalDateTime.now());
            message.setLida(false);
            message.setSender(senderOpt.get());
            message.setReceiver(receiverOpt.get());

            Message savedMessage = messageRepository.save(message);
            log.info("Message sent successfully with ID: {}", savedMessage.getId());
            return Optional.of(savedMessage);
        }
        
        log.warn("Failed to send message - sender ID: {} or receiver ID: {} not found", senderId, receiverId);
        return Optional.empty();
    }

    public Message save(Message message) {
        log.debug("Saving message");
        Message savedMessage = messageRepository.save(message);
        log.debug("Message saved with ID: {}", savedMessage.getId());
        return savedMessage;
    }

    public List<Message> listAll() {
        log.debug("Fetching all messages");
        List<Message> messages = messageRepository.findAll();
        log.debug("Found {} messages", messages.size());
        return messages;
    }

    public Optional<Message> findById(Long id) {
        log.debug("Finding message by ID: {}", id);
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent()) {
            log.debug("Message found with ID: {}", id);
        } else {
            log.debug("Message not found with ID: {}", id);
        }
        return message;
    }

    public Optional<Message> update(Long id, Message updatedMessage) {
        log.info("Updating message with ID: {}", id);
        
        return messageRepository.findById(id)
                .map(existingMessage -> {
                    existingMessage.setText(updatedMessage.getText());
                    existingMessage.setLida(updatedMessage.isLida());
                    Message savedMessage = messageRepository.save(existingMessage);
                    log.info("Message updated successfully with ID: {}", id);
                    return savedMessage;
                });
    }

    public boolean delete(Long id) {
        log.info("Deleting message with ID: {}", id);
        
        return messageRepository.findById(id)
                .map(message -> {
                    messageRepository.delete(message);
                    log.info("Message deleted successfully with ID: {}", id);
                    return true;
                })
                .orElseGet(() -> {
                    log.warn("Message not found for deletion with ID: {}", id);
                    return false;
                });
    }
}