package com.example.colis.service;

import com.example.colis.model.Enums.Specialite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public interface TransporteurService {

    Page<UserDTO> getAllTransporteurs(Pageable pageable);

    Page<UserDTO> getTransporteursBySpecialite(Specialite specialite, Pageable pageable);

    UserDTO createTransporteur(UserDTO dto);

    UserDTO updateTransporteur(String id, UserDTO dto);

    void deleteTransporteur(String id);
}
