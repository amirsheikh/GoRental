package com.example.renal.GoRental.service;

import com.example.renal.GoRental.controller.auth.dto.GetTokenResponse;
import com.example.renal.GoRental.model.User;
import com.example.renal.GoRental.repository.UserRepository;
import com.example.renal.GoRental.controller.auth.dto.LoginRequest;
import com.example.renal.GoRental.controller.auth.dto.SignupRequest;
import com.example.renal.GoRental.security.JwtUtil;
import com.example.renal.GoRental.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    @Test
    void testLoginSuccess() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("user1", "password");
        User user = new User();
        user.setUsername("user1");
        user.setPassword("$2a$10$V5O5OLh1oU8yZ/l72P8SeOCcft9klGkG1zvHH3A5XKrMIBhVtQ.TK"); // Encoded password for "password"

        when(userRepository.findByUsername("user1")).thenReturn(user);
        when(passwordEncoder.matches("password", user.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(user)).thenReturn("generatedAccessToken");
        when(jwtUtil.generateRefreshToken(user)).thenReturn("generatedRefreshToken");

        // Act
        GetTokenResponse response = userService.login(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals("generatedAccessToken", response.getAccessToken());
        assertEquals("generatedRefreshToken", response.getRefreshToken());

        verify(userRepository).findByUsername("user1");
        verify(passwordEncoder).matches("password", user.getPassword());
    }

    @Test
    void testLoginInvalidCredentials() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("user1", "wrongPassword");
        User user = new User();
        user.setUsername("user1");
        user.setPassword("$2a$10$V5O5OLh1oU8yZ/l72P8SeOCcft9klGkG1zvHH3A5XKrMIBhVtQ.TK"); // Encoded password for "password"

        when(userRepository.findByUsername("user1")).thenReturn(user);
        when(passwordEncoder.matches("wrongPassword", user.getPassword())).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.login(loginRequest));
        assertEquals("Invalid credentials", exception.getMessage());

        verify(userRepository).findByUsername("user1");
        verify(passwordEncoder).matches("wrongPassword", user.getPassword());
    }

    @Test
    void testRefreshTokenSuccess() {
        // Arrange
        String refreshToken = "validRefreshToken";
        String username = "user1";
        User user = new User();
        user.setUsername(username);
        when(jwtUtil.isTokenValid(refreshToken)).thenReturn(true);
        when(jwtUtil.getUsernameFromToken(refreshToken)).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(user);
        when(jwtUtil.generateToken(user)).thenReturn("newGeneratedAccessToken");
        when(jwtUtil.generateRefreshToken(user)).thenReturn("newGeneratedRefreshToken");

        // Act
        GetTokenResponse response = userService.refreshToken(refreshToken);

        // Assert
        assertNotNull(response);
        assertEquals("newGeneratedAccessToken", response.getAccessToken());
        assertEquals("newGeneratedRefreshToken", response.getRefreshToken());

        verify(jwtUtil).isTokenValid(refreshToken);
        verify(jwtUtil).getUsernameFromToken(refreshToken);
        verify(userRepository).findByUsername(username);
    }

    @Test
    void testRefreshTokenInvalid() {
        // Arrange
        String refreshToken = "invalidRefreshToken";
        when(jwtUtil.isTokenValid(refreshToken)).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.refreshToken(refreshToken));
        assertEquals("Invalid Refresh Token", exception.getMessage());

        verify(jwtUtil).isTokenValid(refreshToken);
    }

    @Test
    void testRegisterUserSuccess() {
        // Arrange
        SignupRequest signupRequest = new SignupRequest("newuser", "newuser", "newemail@example.com", "newpassword");
        User existingUser = new User();
        existingUser.setUsername("existinguser");

        when(userRepository.findByUsername("newuser")).thenReturn(null); // No existing user with this username
        when(passwordEncoder.encode("newpassword")).thenReturn("encodedNewPassword");
        when(userRepository.save(any(User.class))).thenReturn(new User());

        // Act
        User user = userService.registerUser(signupRequest);

        // Assert
        assertNotNull(user);
        verify(userRepository).findByUsername("newuser");
        verify(passwordEncoder).encode("newpassword");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testRegisterUserUsernameAlreadyExists() {
        // Arrange
        SignupRequest signupRequest = new SignupRequest("existinguser", "existinguser", "email@example.com", "password");
        User existingUser = new User();
        existingUser.setUsername("existinguser");

        when(userRepository.findByUsername("existinguser")).thenReturn(existingUser);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.registerUser(signupRequest));
        assertEquals("Username already exists", exception.getMessage());

        verify(userRepository).findByUsername("existinguser");
    }
}
