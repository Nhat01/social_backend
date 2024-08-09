package com.nhat.social.service;

import com.nhat.social.model.Chat;
import com.nhat.social.model.Message;
import com.nhat.social.model.User;

import java.util.List;

public interface MessageService {
    Message createMessage(User user, Integer chatId, Message message) throws Exception;


    List<Message> findChatMessages(Integer chatId);
}
