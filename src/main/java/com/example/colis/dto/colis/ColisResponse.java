package com.example.colis.dto.colis;

import com.example.colis.model.Enums.ColisStatus;
import com.example.colis.model.Enums.ColisType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColisResponse {

    private String id;
    private ColisType type;
    private Double poids;
    private String adresseDestination;
    private ColisStatus statut;
    private String transporteurId;

    private String instructionsManutention;

    private Double temperatureMin;
    private Double temperatureMax;

}
