package com.nhat.social.service;

import com.nhat.social.exception.PostException;
import com.nhat.social.exception.UserException;
import com.nhat.social.model.Post;

import java.util.List;

public interface PostService {
    Post createPost(Post post, Integer userId) throws UserException, PostException;
    String deletePost(Integer postId, Integer userId) throws UserException,PostException;
    List<Post> findPostByUserId(Integer userId) throws UserException;
    Post findPostById(Integer postId) throws PostException;
    List<Post> findAllPost() throws PostException;
    Post savedPost(Integer postId, Integer userId) throws UserException,PostException;
    Post likePost(Integer postId, Integer userId) throws UserException,PostException;
}
