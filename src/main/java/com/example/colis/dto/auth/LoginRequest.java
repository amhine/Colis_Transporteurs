package com.example.colis.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Login est obligatoire")
    private String login;

    @NotBlank(message = "Password est obligatoire")
    private String password;

}
