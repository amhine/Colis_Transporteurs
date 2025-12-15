package com.example.colis.dto.user;

import com.example.colis.model.Enums.Role;
import com.example.colis.model.Enums.Specialite;
import com.example.colis.model.Enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String id;
    private String login;
    private Role role;
    private Boolean active;
    private UserStatus statut;
    private Specialite specialite;

}
