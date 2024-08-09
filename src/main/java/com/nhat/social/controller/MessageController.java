package com.nhat.social.controller;

import com.nhat.social.exception.UserException;
import com.nhat.social.model.Message;
import com.nhat.social.model.User;
import com.nhat.social.service.MessageService;
import com.nhat.social.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private MessageService messageService;
    private UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @PostMapping("/chat/{chatId}")
    public ResponseEntity<Message> createMessage(@RequestHeader("Authorization") String jwt, @RequestBody Message message, @PathVariable Integer chatId) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Message createdMessage = messageService.createMessage(user, chatId, message);
        return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> findChatMessages(@PathVariable Integer chatId) throws Exception {
        List<Message> messages = messageService.findChatMessages(chatId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
