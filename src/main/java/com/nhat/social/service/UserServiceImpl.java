package com.nhat.social.service;

import com.nhat.social.config.JwtProvider;
import com.nhat.social.exception.UserException;
import com.nhat.social.model.User;
import com.nhat.social.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(User user) throws UserException {
        User isExistingUser = userRepository.findByEmail(user.getEmail());
        if(isExistingUser != null){
            throw new UserException("User is existed");
        }
        User newUser = new User();
        if(user.getLastName() != null && user.getLastName() != null && user.getEmail() != null && user.getPassword() != null && user.getGender() != null){
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            newUser.setGender(user.getGender());
            return userRepository.save(newUser);
        }
        throw new UserException("not enough");
    }

    @Override
    public User findUserById(Integer id) throws UserException {
        Optional<User> opt = userRepository.findById(id);
        if(opt.isPresent()) {
            return opt.get();
        }
        throw new UserException("user not found with id: " + id);
    }

    @Override
    public User findUserByEmail(String email) throws UserException {
        User user = userRepository.findByEmail(email);
        if(user != null) {
            return user;
        }
        throw new UserException("user not found with email: " + email);
    }

    @Override
    public User followUser(Integer userId, Integer followUserId) throws UserException {
        User user1 = findUserById(userId);
        User user2 = findUserById(followUserId);

        user1.getFollowings().add(user2.getId());
        user2.getFollowers().add(user1.getId());

        userRepository.save(user1);
        userRepository.save(user2);
        return user1;
    }

    @Override
    public Set<User> searchUser(String query) throws UserException {
        return userRepository.searchUser(query);
    }

    @Override
    public User updateUserDetails(User user, Integer userId) throws UserException {
        User oldUser = findUserById(userId);
        if(user.getFirstName()!=null){
            oldUser.setFirstName(user.getFirstName());
        }
        if(user.getLastName()!=null){
            oldUser.setLastName(user.getLastName());
        }
        if(user.getEmail()!=null){
            oldUser.setEmail(user.getEmail());
        }
        if(user.getGender()!=null){
            oldUser.setGender(user.getGender());
        }
        return userRepository.save(oldUser);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email = JwtProvider.getEmailFromToken(jwt);
        return userRepository.findByEmail(email);
    }

    @Override
    public void updatePassword(User user, String newPassword) {

    }

    @Override
    public void sendPasswordResetEmail(User user) {

    }
}
