package com.example.colis.mapper;

import com.example.colis.dto.request.CreateColisRequest;
import com.example.colis.dto.request.UpdateColisRequest;
import com.example.colis.dto.response.ColisResponse;
import com.example.colis.model.Colis;
import com.example.colis.model.FragileColis;
import com.example.colis.model.FrigoColis;
import com.example.colis.model.StandardColis;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ColisMapper {

    public ColisResponse toResponse(Colis colis) {
        if (colis == null) {
            return null;
        }
        ColisResponse.ColisResponseBuilder builder = ColisResponse.builder()
                .id(colis.getId())
                .type(colis.getType())
                .poids(colis.getPoids())
                .adresseDestination(colis.getAdresseDestination())
                .statut(colis.getStatut())
                .transporteurId(colis.getTransporteurId()); // <-- correction ici

        if (colis instanceof FragileColis) {
            FragileColis fragile = (FragileColis) colis;
            builder.instructionsManutention(fragile.getInstructionsManutention());
        } else if (colis instanceof FrigoColis) {
            FrigoColis frigo = (FrigoColis) colis;
            builder.temperatureMin(frigo.getTemperatureMin())
                    .temperatureMax(frigo.getTemperatureMax());
        }

        return builder.build();
    }


    public ColisResponse toResponse(Colis colis, String transporteurLogin) {
        ColisResponse response = toResponse(colis);
        if (response != null) {
            response.setTransporteurLogin(transporteurLogin);
        }
        return response;
    }

    public List<ColisResponse> toResponseList(List<Colis> colisList) {
        if (colisList == null) {
            return null;
        }
        return colisList.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Colis toEntity(CreateColisRequest request) {
        if (request == null) {
            return null;
        }

        Colis colis;

        switch (request.getType()) {
            case FRAGILE:
                FragileColis fragile = new FragileColis();
                fragile.setInstructionsManutention(request.getInstructionsManutention());
                colis = fragile;
                break;

            case FRIGO:
                FrigoColis frigo = new FrigoColis();
                frigo.setTemperatureMin(request.getTemperatureMin());
                frigo.setTemperatureMax(request.getTemperatureMax());
                colis = frigo;
                break;

            case STANDARD:
            default:
                colis = new StandardColis();
                break;
        }

        colis.setPoids(request.getPoids());
        colis.setAdresseDestination(request.getAdresseDestination());

        return colis;
    }

    public void updateEntityFromRequest(UpdateColisRequest request, Colis colis) {
        if (request == null || colis == null) {
            return;
        }

        if (request.getPoids() != null) {
            colis.setPoids(request.getPoids());
        }

        if (request.getAdresseDestination() != null) {
            colis.setAdresseDestination(request.getAdresseDestination());
        }

        if (colis instanceof FragileColis && request.getInstructionsManutention() != null) {
            ((FragileColis) colis).setInstructionsManutention(request.getInstructionsManutention());
        }

        if (colis instanceof FrigoColis) {
            FrigoColis frigo = (FrigoColis) colis;
            if (request.getTemperatureMin() != null) {
                frigo.setTemperatureMin(request.getTemperatureMin());
            }
            if (request.getTemperatureMax() != null) {
                frigo.setTemperatureMax(request.getTemperatureMax());
            }
        }
    }


}
