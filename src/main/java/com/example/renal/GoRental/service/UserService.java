package com.example.renal.GoRental.service;
import com.example.renal.GoRental.controller.auth.dto.GetTokenResponse;
import com.example.renal.GoRental.model.User;
import com.example.renal.GoRental.model.UserRoles;
import com.example.renal.GoRental.repository.UserRepository;
import com.example.renal.GoRental.controller.auth.dto.LoginRequest;
import com.example.renal.GoRental.controller.auth.dto.SignupRequest;
import com.example.renal.GoRental.security.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;


    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public GetTokenResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        log.info(user);
        if (user != null) {
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return new GetTokenResponse(jwtUtil.generateToken(user), jwtUtil.generateRefreshToken(user));
            }
        }

        throw new RuntimeException("Invalid credentials");
    }

    public GetTokenResponse refreshToken(String refreshToken) {

        if (jwtUtil.isTokenValid(refreshToken)) {
            String username = jwtUtil.getUsernameFromToken(refreshToken);
            User user = userRepository.findByUsername(username);
            String newAccessToken = jwtUtil.generateToken(user);
            String newRefreshToken = jwtUtil.generateRefreshToken(user);
            return new GetTokenResponse(newAccessToken, newRefreshToken);
        } else {
            throw new RuntimeException("Invalid Refresh Token");
        }
    }

    public User registerUser(SignupRequest signupRequest) {
        if (userRepository.findByUsername(signupRequest.getName()) != null) {
            throw new RuntimeException("Username already exists");
        }

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setUsername(signupRequest.getUsername());

        user.setPassword(encodedPassword);
        user.setRole(UserRoles.USER);

        return userRepository.save(user);
    }
}
