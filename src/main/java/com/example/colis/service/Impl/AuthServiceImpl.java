package com.example.colis.service.Impl;

import com.example.colis.dto.response.LoginResponse;
import com.example.colis.exception.UserInactiveException;
import com.example.colis.model.User;
import com.example.colis.repository.UserRepository;
import com.example.colis.service.AuthService;
import com.example.colis.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByLogin(request.getLogin())
                .orElseThrow(() -> new InvalidCredentialsException("Login ou mot de passe incorrect"));

        if (!user.isActive()) {
            throw new UserInactiveException("Votre compte a été désactivé. Contactez un administrateur.");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Login ou mot de passe incorrect");
        }

        String token = jwtService.generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .login(user.getLogin())
                .role(user.getRole())
                .build();
    }
}
