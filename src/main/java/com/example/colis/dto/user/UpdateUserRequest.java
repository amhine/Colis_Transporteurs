package com.example.colis.dto.user;

import com.example.colis.model.Enums.Specialite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UpdateUserRequest {

    private String login;
    private String password;
    private Boolean active;

    private StatutTransporteur statut;
    private Specialite specialite;

}
