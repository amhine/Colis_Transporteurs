package com.example.colis.dto.request;


import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateColisRequest {
    @DecimalMin(value = "0.1", message = "Le poids doit être supérieur à 0")
    private Double poids;
    private String adresseDestination;
    private String instructionsManutention;
    private Double temperatureMin;
    private Double temperatureMax;
}
