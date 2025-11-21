package com.example.colis.service;

import com.example.colis.model.Colis;
import com.example.colis.model.Enums.StatutColis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ColisService {
    Colis createColis(Colis colis);

    Optional<Colis> getColisById(String id);

    List<Colis> getAllColis();

    List<Colis> getColisByType(String type);

    List<Colis> getColisByStatut(StatutColis statut);

    List<Colis> getColisByAdresse(String adresse);

    Colis updateColis(Colis colis);

    void deleteColis(String id);

    Colis updateStatut(String id, StatutColis statut);

    Page<Colis> findByTransporteurId(String transporteurId, Pageable pageable);

}
