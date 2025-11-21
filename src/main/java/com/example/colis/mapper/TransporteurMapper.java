package com.example.colis.mapper;

import com.example.colis.dto.request.CreateTransporteurRequest;
import com.example.colis.dto.request.UpdateTransporteurRequest;
import com.example.colis.dto.response.TransporteurResponse;
import com.example.colis.model.Enums.StatutTransporteur;
import com.example.colis.model.Transporteur;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransporteurMapper {

    public TransporteurResponse toResponse(Transporteur transporteur) {
        if (transporteur == null) {
            return null;
        }

        return TransporteurResponse.builder()
                .id(transporteur.getId())
                .login(transporteur.getLogin())
                .specialite(transporteur.getSpecialite())
                .status(transporteur.getStatus())
                .active(transporteur.isActive())
                .build();
    }
    
    public List<TransporteurResponse> toResponseList(List<Transporteur> transporteurs) {
        if (transporteurs == null) {
            return null;
        }
        return transporteurs.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Transporteur toEntity(CreateTransporteurRequest request) {
        if (request == null) {
            return null;
        }

        Transporteur transporteur = new Transporteur();
        transporteur.setLogin(request.getLogin());
        transporteur.setPassword(request.getPassword());
        transporteur.setSpecialite(request.getSpecialite());
        transporteur.setStatus(StatutTransporteur.DISPONIBLE);
        transporteur.setActive(true);

        return transporteur;
    }

    public void updateEntityFromRequest(UpdateTransporteurRequest request, Transporteur transporteur) {
        if (request == null || transporteur == null) {
            return;
        }

        if (request.getLogin() != null) {
            transporteur.setLogin(request.getLogin());
        }

        if (request.getPassword() != null) {
            transporteur.setPassword(request.getPassword());
        }

        if (request.getSpecialite() != null) {
            transporteur.setSpecialite(request.getSpecialite());
        }

        if (request.getStatus() != null) {
            transporteur.setStatus(request.getStatus());
        }

        if (request.getActive() != null) {
            transporteur.setActive(request.getActive());
        }
    }



}
