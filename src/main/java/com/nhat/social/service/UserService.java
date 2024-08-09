package com.nhat.social.service;

import com.nhat.social.exception.UserException;
import com.nhat.social.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> getAllUsers();
    User registerUser(User user) throws UserException;
    User findUserById(Integer id) throws UserException;
    User findUserByEmail(String email) throws UserException;
    User followUser(Integer userId,Integer followUserId) throws UserException;
    Set<User> searchUser(String query) throws UserException;
    User updateUserDetails(User user, Integer userId) throws UserException;
    User findUserProfileByJwt(String jwt) throws UserException;
    void updatePassword(User user, String newPassword);
    void sendPasswordResetEmail(User user);
}
