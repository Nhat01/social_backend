package com.nhat.social.service;

import com.nhat.social.exception.PostException;
import com.nhat.social.exception.UserException;
import com.nhat.social.model.Comment;
import com.nhat.social.model.Post;
import com.nhat.social.model.User;
import com.nhat.social.repository.CommentRepository;
import com.nhat.social.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;
    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostService postService, UserService userService, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postService = postService;
        this.userService = userService;
        this.postRepository = postRepository;
    }

    @Override
    public Comment createComment(Comment comment, Integer postId, Integer userId) throws UserException, PostException {
        User user = userService.findUserById(userId);
        Post post = postService.findPostById(postId);

        comment.setContent(comment.getContent());
        comment.setUser(user);
        comment.setCreatedAt(LocalDateTime.now());
        Comment newComment = commentRepository.save(comment);

        post.getComments().add(comment);
        postRepository.save(post);
        return newComment;
    }

    @Override
    public Comment findCommentById(Integer id) throws Exception {
        Optional<Comment> opt = commentRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new Exception("aaa");
    }

    @Override
    public Comment findCommentByUserId(Integer id) {

        return null;
    }

    @Override
    public Comment likeComment(Integer commentId, Integer userId) throws Exception {
        Comment comment = findCommentById(commentId);
        User user = userService.findUserById(userId);
        if (!comment.getLiked().contains(user)){
            comment.getLiked().add(user);
        } else comment.getLiked().remove(user);

        return commentRepository.save(comment);
    }
}
