package com.nhat.social.controller;

import com.nhat.social.exception.UserException;
import com.nhat.social.model.Chat;
import com.nhat.social.model.User;
import com.nhat.social.request.CreateChatRequest;
import com.nhat.social.service.ChatService;
import com.nhat.social.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
    private ChatService chatService;
    private UserService userService;

    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @PostMapping("/{userId2}")
    public ResponseEntity<Chat> createChat(@RequestHeader("Authorization") String jwt,@PathVariable Integer userId2) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        User user2 = userService.findUserById(userId2);
        Chat chat = chatService.createChat(user, user2);
        return new ResponseEntity<>(chat, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Chat>> findUsersChat(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        List<Chat> chats = chatService.findUsersChat(user.getId());
        return new ResponseEntity<>(chats, HttpStatus.OK);
    }
}
