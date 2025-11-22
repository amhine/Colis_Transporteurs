package com.example.colis.repository;

import com.example.colis.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,Long> {

    Optional<User> findByLogin(String login);

    Page<User> findAll(Pageable pageable);

    @Query("{ 'role' : ?0 }")
    Page<User> findByRole(String role, Pageable pageable);

    @Query("{ 'role' : 'TRANSPORTEUR', 'specialite' : ?0 }")
    Page<User> findTransporteursBySpecialite(String specialite, Pageable pageable);

    @Query("{ 'role' : 'TRANSPORTEUR', 'active' : ?0 }")
    Page<User> findTransporteursByActive(boolean active, Pageable pageable);

    @Query("{ 'role' : 'TRANSPORTEUR', 'statut' : ?0 }")
    Page<User> findTransporteursByStatut(String statut, Pageable pageable);



}
