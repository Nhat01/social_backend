package com.nhat.social.service;

import com.nhat.social.exception.UserException;
import com.nhat.social.model.Reel;
import com.nhat.social.model.User;

import java.util.List;

public interface ReelService {
    Reel createReel(Reel reel, User user);
    List<Reel> findAllReels();
    List<Reel> findUserReels(Integer userId) throws UserException;
}
