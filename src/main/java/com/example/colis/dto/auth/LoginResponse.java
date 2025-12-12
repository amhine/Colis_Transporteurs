package com.example.colis.dto.auth;

import com.example.colis.model.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private String login;
    private Role role;
}
