package com.nhat.social.service;

import com.nhat.social.exception.PostException;
import com.nhat.social.exception.UserException;
import com.nhat.social.model.Comment;

public interface CommentService {
    Comment createComment(Comment comment, Integer postId, Integer userId) throws UserException, PostException;
    Comment findCommentById(Integer id) throws Exception;
    Comment findCommentByUserId(Integer id);
    Comment likeComment(Integer commentId, Integer userId) throws Exception;
}
