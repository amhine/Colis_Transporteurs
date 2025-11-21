package com.example.colis.dto.response;

import com.example.colis.model.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;

    @Builder.Default
    private String tokenType = "Bearer";
    private Long userId;
    private String login;
    private Role role;
}
