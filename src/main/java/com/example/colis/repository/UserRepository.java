package com.example.colis.repository;

import com.example.colis.model.Enums.Specialite;
import com.example.colis.model.Enums.UserStatus;
import com.example.colis.model.Transporteur;
import com.example.colis.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

    Optional<User> findByLogin(String login);

    @Query("{ '_class' : 'com.colis.model.Transporteur' }")
    Page<Transporteur> findAllTransporteurs(Pageable pageable);

    @Query("{ '_class' : 'com.colis.model.Transporteur', 'specialite' : ?0 }")
    Page<Transporteur> findTransporteursBySpecialite(Specialite specialite, Pageable pageable);

    @Query("{ '_class' : 'com.colis.model.Transporteur', 'statut' : ?0 }")
    Page<Transporteur> findTransporteursByStatut(UserStatus statut, Pageable pageable);



}
