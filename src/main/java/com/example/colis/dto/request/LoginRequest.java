package com.example.colis.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "login est obligatoire")
    private String login;

    @NotBlank(message = "password est obligatoire")
    private String password;
}
