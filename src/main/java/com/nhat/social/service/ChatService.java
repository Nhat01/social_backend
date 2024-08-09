package com.nhat.social.service;

import com.nhat.social.model.Chat;
import com.nhat.social.model.User;

import java.util.List;

public interface ChatService {
    Chat createChat(User reqUser, User user2);
    Chat findChatById(Integer chatId) throws Exception;
    List<Chat> findUsersChat(Integer userId);
}
