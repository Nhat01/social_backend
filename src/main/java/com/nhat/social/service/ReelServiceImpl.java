package com.nhat.social.service;

import com.nhat.social.exception.UserException;
import com.nhat.social.model.Reel;
import com.nhat.social.model.User;
import com.nhat.social.repository.ReelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReelServiceImpl implements ReelService{
    private ReelRepository reelRepository;
    private UserService userService;

    public ReelServiceImpl(ReelRepository reelRepository, UserService userService) {
        this.reelRepository = reelRepository;
        this.userService = userService;
    }

    @Override
    public Reel createReel(Reel reel, User user) {
        Reel createdReel = new Reel();
        createdReel.setTitle(reel.getTitle());
        createdReel.setVideo(reel.getVideo());
        createdReel.setUser(user);
        return reelRepository.save(createdReel);
    }

    @Override
    public List<Reel> findAllReels() {
        return reelRepository.findAll();
    }

    @Override
    public List<Reel> findUserReels(Integer userId) throws UserException {
        User user = userService.findUserById(userId);
        return null;
    }
}
