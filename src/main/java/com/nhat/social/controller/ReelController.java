package com.nhat.social.controller;

import com.nhat.social.exception.UserException;
import com.nhat.social.model.Reel;
import com.nhat.social.model.User;
import com.nhat.social.service.ReelService;
import com.nhat.social.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reels")
public class ReelController {
    private ReelService reelsService;
    private UserService userService;

    public ReelController(ReelService reelsService, UserService userService) {
        this.reelsService = reelsService;
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<Reel> createReel(@RequestBody Reel reel, @RequestHeader("Authorization") String jwt) throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt);
        Reel createdReel = reelsService.createReel(reel,reqUser);
        return new ResponseEntity<>(createdReel, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Reel>> getAllReels() {
        List<Reel> reels = reelsService.findAllReels();
        return new ResponseEntity<>(reels, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Reel>> getUserReels(@RequestHeader("Authorization") String jwt) throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt);
        List<Reel> reels = reelsService.findUserReels(reqUser.getId());
        return new ResponseEntity<>(reels, HttpStatus.OK);
    }
}
