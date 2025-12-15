package com.example.colis.service;

import com.example.colis.dto.PageResponse;
import com.example.colis.dto.user.CreateUserRequest;
import com.example.colis.dto.user.UpdateUserRequest;
import com.example.colis.dto.user.UserResponse;
import com.example.colis.model.Enums.Specialite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public interface TransporteurService {

    PageResponse<UserResponse> getAllTransporteurs(int page, int size);
    PageResponse<UserResponse> getTransporteursBySpecialite(Specialite specialite, int page, int size);
    UserResponse createTransporteur(CreateUserRequest request);
    UserResponse updateTransporteur(String id, UpdateUserRequest request);
    void deleteTransporteur(String id);
}
