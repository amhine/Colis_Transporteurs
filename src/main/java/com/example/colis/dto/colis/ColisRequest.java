package com.example.colis.dto.colis;

import com.example.colis.model.Enums.ColisType;
import com.example.colis.model.Enums.Specialite;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColisRequest {

    @NotNull(message = "Type est obligatoire")
    private ColisType type;

    @NotNull(message = "Poids est obligatoire")
    @Positive(message = "Poids doit être positif")
    private Double poids;

    @NotBlank(message = "Adresse de destination est obligatoire")
    private String adresseDestination;

    private String instructionsManutention;
    private Double temperatureMin;
    private Double temperatureMax;

}
