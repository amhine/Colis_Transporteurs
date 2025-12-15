package com.example.colis.service;

import com.example.colis.dto.auth.LoginRequest;
import com.example.colis.dto.auth.LoginResponse;;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
