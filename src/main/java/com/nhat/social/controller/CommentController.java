package com.nhat.social.controller;

import com.nhat.social.exception.PostException;
import com.nhat.social.exception.UserException;
import com.nhat.social.model.Comment;
import com.nhat.social.model.User;
import com.nhat.social.service.CommentService;
import com.nhat.social.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private CommentService commentService;
    private UserService userService;

    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @PostMapping("/post/{postId}")
    private ResponseEntity<Comment> createComment(@RequestHeader("Authorization") String jwt, @RequestBody Comment comment,@PathVariable Integer postId) throws UserException, PostException {
        User reqUser = userService.findUserProfileByJwt(jwt);
        Comment newComment = commentService.createComment(comment, postId, reqUser.getId());
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }

    @PostMapping("/like/{commentId}")
    private ResponseEntity<Comment> likeComment(@RequestHeader("Authorization") String jwt, @PathVariable Integer commentId) throws Exception {
        User reqUser = userService.findUserProfileByJwt(jwt);
        Comment likedComment = commentService.likeComment(commentId, reqUser.getId());
        return new ResponseEntity<>(likedComment, HttpStatus.CREATED);
    }
}
