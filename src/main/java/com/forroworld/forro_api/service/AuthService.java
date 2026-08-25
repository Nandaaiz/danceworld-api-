package com.forroworld.forro_api.service;

import com.forroworld.forro_api.dto.AuthResponse;
import com.forroworld.forro_api.dto.LoginRequest;
import com.forroworld.forro_api.dto.RegisterRequest;
import com.forroworld.forro_api.exception.BadRequestException;
import com.forroworld.forro_api.exception.InvalidCredentialsException;
import com.forroworld.forro_api.model.User;
import com.forroworld.forro_api.repository.UserRepository;
import com.forroworld.forro_api.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email já cadastrado");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setUserType(User.UserType.valueOf(request.getUserType()));

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token, user.getEmail(), user.getUserType().name());
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Email ou senha inválidos"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException("Email ou senha inválidos");
        }

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token, user.getEmail(), user.getUserType().name());
    }
}