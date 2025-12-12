package com.example.colis.dto.user;

import com.example.colis.model.Enums.Role;
import com.example.colis.model.Enums.Specialite;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "Login est obligatoire")
    private String login;

    @NotBlank(message = "Password est obligatoire")
    private String password;

    @NotNull(message = "Role est obligatoire")
    private Role role;

    private StatutTransporteur statut;
    private Specialite specialite;

}
