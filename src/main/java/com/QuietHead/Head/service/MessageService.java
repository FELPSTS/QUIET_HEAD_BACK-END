package com.QuietHead.Head.service;

import com.QuietHead.Head.domain.Client;
import com.QuietHead.Head.domain.Message;
import com.QuietHead.Head.repository.ClientRepository;
import com.QuietHead.Head.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ClientRepository clientRepository;

    public Optional<Message> sendMessage(Long senderId, Long receiverId, Message message) {
        Optional<Client> senderOpt = clientRepository.findById(senderId);
        Optional<Client> receiverOpt = clientRepository.findById(receiverId);

        if (senderOpt.isPresent() && receiverOpt.isPresent()) {
            message.setText(message.getText());
            message.setDataHora(LocalDateTime.now());
            message.setLida(false);

            message.setSender(senderOpt.get());
            message.setReceiver(receiverOpt.get());

            Message saved = messageRepository.save(message);
            return Optional.of(saved);
        }
        return Optional.empty();
    }

    public Message save(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> listAll() {
        return messageRepository.findAll();
    }

    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    public Optional<Message> update(Long id, Message updatedMessage) {
        return messageRepository.findById(id).map(existingMessage -> {
            existingMessage.setText(updatedMessage.getText());
            existingMessage.setLida(updatedMessage.isLida());
            return messageRepository.save(existingMessage);
        });
    }

    public boolean delete(Long id) {
        return messageRepository.findById(id).map(message -> {
            messageRepository.delete(message);
            return true;
        }).orElse(false);
    }
}
