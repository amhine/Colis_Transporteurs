package com.example.colis.service;

import com.example.colis.dto.request.LoginRequest;
import com.example.colis.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
