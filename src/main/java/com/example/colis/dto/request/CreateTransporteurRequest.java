package com.example.colis.dto.request;


import com.example.colis.model.Enums.Specialite;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransporteurRequest {
    @NotBlank(message = "login est obligatoire")
    @Size(min = 3, max = 20, message = "login doit contenir entre 3 et 20 caractères")
    private String login;

    @NotBlank(message = "password est obligatoire")
    @Size(min = 6, message = "password doit contenir au moins 6 caractères")
    private String password;

    @NotNull(message = "spécialité est obligatoire")
    private Specialite specialite;
}
