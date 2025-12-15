package com.example.colis.repository;

import com.example.colis.model.Colis;
import com.example.colis.model.Enums.Specialite;
import com.example.colis.model.Enums.ColisStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ColisRepository extends MongoRepository<Colis, String> {
    Page<Colis> findByTransporteurId(String transporteurId, Pageable pageable);

    @Query("{ '_class' : ?0 }")
    Page<Colis> findByClass(String className, Pageable pageable);

    Page<Colis> findByStatut(ColisStatus statut, Pageable pageable);

    @Query("{ '_class' : ?0, 'statut' : ?1 }")
    Page<Colis> findByClassAndStatut(String className, ColisStatus statut, Pageable pageable);

    Page<Colis> findByAdresseDestinationContainingIgnoreCase(String destination, Pageable pageable);

    Page<Colis> findByTransporteurIdAndAdresseDestinationContainingIgnoreCase(
            String transporteurId, String destination, Pageable pageable);

    @Query("{ 'transporteurId' : ?0, '_class' : ?1 }")
    Page<Colis> findByTransporteurIdAndClass(String transporteurId, String className, Pageable pageable);

    Page<Colis> findByTransporteurIdAndStatut(String transporteurId, ColisStatus statut, Pageable pageable);

    @Query("{ 'transporteurId' : ?0, '_class' : ?1, 'statut' : ?2 }")
    Page<Colis> findByTransporteurIdAndClassAndStatut(
            String transporteurId, String className, ColisStatus statut, Pageable pageable);
}
