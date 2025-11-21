package com.example.colis.dto.response;

import com.example.colis.model.Enums.Role;
import com.example.colis.model.Enums.Specialite;
import com.example.colis.model.Enums.StatutTransporteur;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private Long id;
    private String login;
    private Role role;
    private Boolean active;
    private Specialite specialite;
    private StatutTransporteur status;
}
