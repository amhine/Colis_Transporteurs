package com.example.colis.service.Impl;

import com.example.colis.dto.auth.LoginRequest;
import com.example.colis.dto.auth.LoginResponse;
import com.example.colis.model.Admin;
import com.example.colis.model.Enums.Role;
import com.example.colis.model.User;
import com.example.colis.repository.UserRepository;
import com.example.colis.security.JwtUtil;
import com.example.colis.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByLogin(request.getLogin())
                .orElseThrow(() -> new BadCredentialsException("Login ou mot de passe incorrect"));

        if (!user.getActive()) {
            throw new BadCredentialsException("Compte désactivé");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Login ou mot de passe incorrect");
        }

        Role role = (user instanceof Admin) ? Role.ADMIN : Role.TRANSPORTEUR;
        String token = jwtUtil.generateToken(user.getLogin(), role);

        return LoginResponse.builder()
                .token(token)
                .login(user.getLogin())
                .role(role)
                .build();
    }
}
