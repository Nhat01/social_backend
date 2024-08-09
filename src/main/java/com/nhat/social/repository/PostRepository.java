package com.nhat.social.repository;

import com.nhat.social.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("select p from Post p where p.user.id = :userId")
    List<Post> findPostByUserId(Integer userId);
}
