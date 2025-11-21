package com.example.colis.dto.request;

import com.example.colis.model.Enums.Specialite;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateColisRequest {
    @NotNull(message = "Le type de colis est obligatoire")
    private Specialite type;

    @NotNull(message = "poids est obligatoire")
    @DecimalMin(value = "0.1", message = "poids doit être supérieur à 0")
    private Double poids;

    @NotBlank(message = "adresse est obligatoire")
    private String adresseDestination;

    private String instructionsManutention;

    private Double temperatureMin;
    private Double temperatureMax;
}
