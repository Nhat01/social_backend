package com.nhat.social.repository;

import com.nhat.social.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    @Query("select u from User u where u.firstName like '%:query%' or u.lastName like '%:query%' or u.email like '%:query%'")
    Set<User> searchUser(@Param("query") String query);
}
