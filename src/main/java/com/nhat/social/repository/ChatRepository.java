package com.nhat.social.repository;

import com.nhat.social.model.Chat;
import com.nhat.social.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Integer> {
    List<Chat> findByUsersId(Integer userId);

    @Query("select c from Chat c where :user MEMBER of c.users and :reqUser member of c.users")
    Chat findChatByUserId(@Param("user") User user,@Param("reqUser") User reqUser);

}
