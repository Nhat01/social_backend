package com.nhat.social.controller;

import com.nhat.social.exception.PostException;
import com.nhat.social.exception.UserException;
import com.nhat.social.model.Post;
import com.nhat.social.model.User;
import com.nhat.social.service.PostService;
import com.nhat.social.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/posts/user")
    public ResponseEntity<Post> createPost(@RequestHeader("Authorization") String jwt,@RequestBody Post post) throws PostException, UserException {
        User reqUser = userService.findUserProfileByJwt(jwt);
        Post createdPost = postService.createPost(post, reqUser.getId());
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PostMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@RequestHeader("Authorization") String jwt,@PathVariable Integer postId) throws PostException, UserException {
        User reqUser = userService.findUserProfileByJwt(jwt);
        String message = postService.deletePost(postId, reqUser.getId());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> findPostById(@PathVariable Integer postId) throws PostException, UserException {
        Post post = postService.findPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @GetMapping("/posts/user")
    public ResponseEntity<List<Post>> getPostByUserId(@RequestHeader("Authorization") String jwt) throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt);
        List<Post> posts = postService.findPostByUserId(reqUser.getId());
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPost() throws PostException {
        List<Post> posts = postService.findAllPost();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @PutMapping("/posts/save/{postId}")
    public ResponseEntity<Post> savePost(@PathVariable Integer postId,@RequestHeader("Authorization") String jwt) throws UserException, PostException {
        User reqUser = userService.findUserProfileByJwt(jwt);
        Post post = postService.savedPost(postId, reqUser.getId());
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
    @PutMapping("/posts/like/{postId}")
    public ResponseEntity<Post> likePost(@PathVariable Integer postId,@RequestHeader("Authorization") String jwt) throws UserException, PostException {
        User reqUser = userService.findUserProfileByJwt(jwt);
        Post post = postService.likePost(postId, reqUser.getId());
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
}
