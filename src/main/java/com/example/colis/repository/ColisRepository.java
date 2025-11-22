package com.example.colis.repository;

import com.example.colis.model.Colis;
import com.example.colis.model.Enums.Specialite;
import com.example.colis.model.Enums.StatutColis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColisRepository extends MongoRepository<Colis, String> {

    Page<Colis> findByStatut(StatutColis statut, Pageable pageable);
    Page<Colis> findByType(Specialite Type,Pageable pageable);
    Page<Colis> findByAdresseDestinationContainingIgnoreCase(String adresse, Pageable pageable);
    Page<Colis> findByTransporteurId(String transporteurId, Pageable pageable);
    Page<Colis> findAll(Colis colis,Pageable pageable);
}
