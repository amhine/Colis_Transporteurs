package com.example.colis.dto.response;

import com.example.colis.model.Enums.Specialite;
import com.example.colis.model.Enums.StatutColis;
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
public class ColisResponse {
    private String id;
    private Specialite type;
    private Double poids;
    private String adresseDestination;
    private StatutColis statut;
    private String transporteurId;
    private String transporteurLogin;
    private String instructionsManutention;
    private Double temperatureMin;
    private Double temperatureMax;
}
