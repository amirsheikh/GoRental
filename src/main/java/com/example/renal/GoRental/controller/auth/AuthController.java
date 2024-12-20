package com.example.renal.GoRental.controller.auth;

import com.example.renal.GoRental.controller.auth.dto.*;
import com.example.renal.GoRental.model.User;
import com.example.renal.GoRental.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;


    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public GetTokenResponse login(@RequestBody LoginRequest loginRequest) {
        try {
            return userService.login(loginRequest);

        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping("refresh")
    public GetTokenResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            return userService.refreshToken(refreshTokenRequest.getRefreshToken());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping("/signup")
    public UserFull signup(@RequestBody SignupRequest signupRequest) {
        try {
            User user = userService.registerUser(signupRequest);
            return UserFull.of(user);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
