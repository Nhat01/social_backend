package com.nhat.social.controller;

import com.nhat.social.config.JwtProvider;
import com.nhat.social.exception.UserException;
import com.nhat.social.model.User;
import com.nhat.social.request.LoginRequest;
import com.nhat.social.response.AuthResponse;
import com.nhat.social.service.CustomUserDetailServiceImpl;
import com.nhat.social.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final PasswordEncoder passwordEncoder;
    private UserService userService;
    private CustomUserDetailServiceImpl userDetailService;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder, CustomUserDetailServiceImpl userDetailService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userDetailService = userDetailService;
    }

    @PostMapping("/users/signup")
    public AuthResponse createUser(@RequestBody User user) throws UserException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userService.registerUser(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser.getEmail(), newUser.getPassword());
        String token = JwtProvider.generateToken(authentication);
        return new AuthResponse(token, "Register Success");
    }
    @PostMapping("/users/signIn")
    public AuthResponse signIn(@RequestBody LoginRequest loginRequest) throws UserException {
        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        String token = JwtProvider.generateToken(authentication);
        return new AuthResponse(token, "Login Success");
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = userDetailService.loadUserByUsername(email);
        if(userDetails == null) {
            throw new BadCredentialsException("invalid user name");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("password not matches");
        }
        System.out.println("authorities: " + userDetails.getAuthorities());
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
