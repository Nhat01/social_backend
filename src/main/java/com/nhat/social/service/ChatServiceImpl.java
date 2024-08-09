package com.nhat.social.service;

import com.nhat.social.model.Chat;
import com.nhat.social.model.User;
import com.nhat.social.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService{
    private ChatRepository chatRepository;

    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public Chat createChat(User reqUser, User user2) {
        Chat isExisted = chatRepository.findChatByUserId(user2, reqUser);
        if (isExisted != null) {
            return isExisted;
        }
        Chat createdChat = new Chat();
        createdChat.getUsers().add(reqUser);
        createdChat.getUsers().add(user2);
        createdChat.setTimestamp(LocalDateTime.now());
        return chatRepository.save(createdChat);
    }

    @Override
    public Chat findChatById(Integer chatId) throws Exception {
        Optional<Chat> opt = chatRepository.findById(chatId);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new Exception("Chat not existing");
    }

    @Override
    public List<Chat> findUsersChat(Integer userId) {
        return chatRepository.findByUsersId(userId);
    }
}
