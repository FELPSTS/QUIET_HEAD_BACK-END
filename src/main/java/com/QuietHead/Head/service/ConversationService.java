package com.QuietHead.Head.service;

import com.QuietHead.Head.domain.Client;
import com.QuietHead.Head.domain.Conversation;
import com.QuietHead.Head.domain.Message;
import com.QuietHead.Head.repository.ClientRepository;
import com.QuietHead.Head.repository.ConversationRepository;
import com.QuietHead.Head.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private MessageRepository messageRepository;

    public Conversation createConversation(List<Long> clientIds) {
        List<Client> clients = new ArrayList<>();
        for (Long id : clientIds) {
            clientRepository.findById(id).ifPresent(clients::add);
        }

        Conversation conversation = new Conversation();
        conversation.setParticipants(clients);
        conversation.setCreatedAt(java.time.LocalDate.now());

        return conversationRepository.save(conversation);
    }

    public Optional<Message> sendMessage(Long conversationId, Long senderId, String text) {
        Optional<Conversation> convOpt = conversationRepository.findById(conversationId);
        Optional<Client> senderOpt = clientRepository.findById(senderId);

        if (convOpt.isPresent() && senderOpt.isPresent()) {
            Conversation conv = convOpt.get();

            Message msg = new Message();
            msg.setText(text);
            msg.setDataHora(LocalDateTime.now());
            msg.setLida(false);
            msg.setSender(senderOpt.get());

            Message savedMessage = messageRepository.save(msg);

            conv.addMessage(savedMessage);
            conversationRepository.save(conv);

            return Optional.of(savedMessage);
        }

        return Optional.empty();
    }

    public Optional<Conversation> getConversation(Long id) {
        return conversationRepository.findById(id);
    }

    public List<Conversation> getAllConversations() {
        return conversationRepository.findAll();
    }

    public boolean attachExistingMessagesToConversation(Long conversationId, List<Long> messageIds) {
    Optional<Conversation> convOpt = conversationRepository.findById(conversationId);

    if (convOpt.isPresent()) {
        Conversation conversation = convOpt.get();

        List<Message> existingMessages = new ArrayList<>();
        for (Long messageId : messageIds) {
            messageRepository.findById(messageId).ifPresent(msg -> {
              
                msg.setConversation(conversation);
                messageRepository.save(msg); 
                existingMessages.add(msg);
            });
        }

        if (conversation.getMessages() == null) {
            conversation.setMessages(new ArrayList<>());
        }

        conversation.getMessages().addAll(existingMessages);
        conversationRepository.save(conversation);

        return true;
    }
    return false;
}
}
