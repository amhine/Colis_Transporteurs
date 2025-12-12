package com.example.colis.mapper;

import com.example.colis.dto.colis.ColisResponse;
import com.example.colis.model.Colis;
import com.example.colis.model.ColisFragile;
import com.example.colis.model.ColisFrigo;
import com.example.colis.model.ColisStandard;
import com.example.colis.model.Enums.ColisType;
import org.springframework.stereotype.Component;

@Component
public class ColisMapper {

    public ColisResponse toResponse(Colis colis) {
        if (colis == null) {
            return null;
        }

        ColisResponse.ColisResponseBuilder builder = ColisResponse.builder()
                .id(colis.getId())
                .poids(colis.getPoids())
                .adresseDestination(colis.getAdresseDestination())
                .statut(colis.getStatut())
                .transporteurId(colis.getTransporteurId());

        if (colis instanceof ColisStandard) {
            builder.type(ColisType.STANDARD);
        } else if (colis instanceof ColisFragile) {
            ColisFragile fragile = (ColisFragile) colis;
            builder.type(ColisType.FRAGILE)
                    .instructionsManutention(fragile.getInstructionsManutention());
        } else if (colis instanceof ColisFrigo) {
            ColisFrigo frigo = (ColisFrigo) colis;
            builder.type(ColisType.FRIGO)
                    .temperatureMin(frigo.getTemperatureMin())
                    .temperatureMax(frigo.getTemperatureMax());
        }

        return builder.build();
    }
}
