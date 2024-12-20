package com.example.renal.GoRental.controller.auth;

import com.example.renal.GoRental.controller.auth.dto.*;
import com.example.renal.GoRental.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<GetTokenResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            GetTokenResponse loginResponse = userService.login(loginRequest);
            return new ResponseEntity<>(loginResponse, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping("refresh")
    public ResponseEntity<GetTokenResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            GetTokenResponse loginResponse =  userService.refreshToken(refreshTokenRequest.getRefreshToken());
            return new ResponseEntity<>(loginResponse, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest signupRequest) {
        try {
            userService.registerUser(signupRequest);
            SignupResponse response = new SignupResponse("User registered successfully", RegistartionStatus.SUCCESS);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
