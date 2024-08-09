package com.nhat.social.service;

import com.nhat.social.model.Chat;
import com.nhat.social.model.Message;
import com.nhat.social.model.User;
import com.nhat.social.repository.ChatRepository;
import com.nhat.social.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService{
    private MessageRepository messageRepository;
    private ChatService chatService;
    private ChatRepository chatRepository;

    public MessageServiceImpl(MessageRepository messageRepository, ChatService chatService, ChatRepository chatRepository) {
        this.messageRepository = messageRepository;
        this.chatService = chatService;
        this.chatRepository = chatRepository;
    }


    @Override
    public Message createMessage(User user, Integer chatId, Message message) throws Exception {
        Chat chat = chatService.findChatById(chatId);
        Message newMessage = new Message();

        newMessage.setChat(chat);
        newMessage.setContent(message.getContent());
        newMessage.setImageUrl(message.getImageUrl());
        newMessage.setUser(user);
        newMessage.setTimestamp(LocalDateTime.now());

        Message savedMessage =messageRepository.save(newMessage);
        chat.getMessages().add(savedMessage);
        chatRepository.save(chat);
        return savedMessage;
    }


    @Override
    public List<Message> findChatMessages(Integer chatId) {
        return messageRepository.findByChatId(chatId);
    }
}
