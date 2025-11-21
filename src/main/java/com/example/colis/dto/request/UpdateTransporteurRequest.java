package com.example.colis.dto.request;

import com.example.colis.model.Enums.Specialite;
import com.example.colis.model.Enums.StatutTransporteur;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTransporteurRequest {
    @Size(min = 3, max = 20, message = "login doit contenir entre 3 et 20 caractères")
    private String login;

    @Size(min = 6, message = "password doit contenir au moins 6 caractères")
    private String password;

    private Specialite specialite;

    private StatutTransporteur status;

    private Boolean active;
}
