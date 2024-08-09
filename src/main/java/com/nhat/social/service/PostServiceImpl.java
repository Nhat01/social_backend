package com.nhat.social.service;

import com.nhat.social.exception.PostException;
import com.nhat.social.exception.UserException;
import com.nhat.social.model.Post;
import com.nhat.social.model.User;
import com.nhat.social.repository.PostRepository;
import com.nhat.social.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, UserService userService, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public Post createPost(Post post, Integer userId) throws UserException, PostException {
        User user = userService.findUserById(userId);
        Post newPost = new Post();
        newPost.setCaption(post.getCaption());
        newPost.setImage(post.getImage());
        newPost.setVideo(post.getVideo());
        newPost.setUser(user);
        newPost.setCreatedDate(LocalDateTime.now());
        return postRepository.save(newPost);
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws UserException, PostException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if(!Objects.equals(post.getUser().getId(), user.getId())){
            throw new PostException("you can't delete another users post");
        }
        postRepository.delete(post);
        return "post deleted";
    }

    @Override
    public List<Post> findPostByUserId(Integer userId) throws UserException {
        return postRepository.findPostByUserId(userId);
    }

    @Override
    public Post findPostById(Integer postId) throws PostException {
        Optional<Post> opt = postRepository.findById(postId);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new PostException("post is not found with id: "+ postId);
    }

    @Override
    public List<Post> findAllPost() throws PostException {
        return postRepository.findAll();
    }

    @Override
    public Post savedPost(Integer postId, Integer userId) throws UserException, PostException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if(user.getSavedPosts().contains(post)){
            user.getSavedPosts().remove(post);
        } else{
            user.getSavedPosts().add(post);
        }
        userRepository.save(user);
        return post;
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws UserException, PostException {
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);
        if(post.getLiked().contains(user)){
            post.getLiked().remove(user);
        } else{
            post.getLiked().add(user);
        }
        return postRepository.save(post);
    }
}
