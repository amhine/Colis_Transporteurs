package com.example.colis.service;

import com.example.colis.dto.PageResponse;
import com.example.colis.dto.colis.AssignColisRequest;
import com.example.colis.dto.colis.ColisRequest;
import com.example.colis.dto.colis.ColisResponse;
import com.example.colis.dto.colis.UpdateStatusRequest;
import com.example.colis.model.Colis;
import com.example.colis.model.Enums.ColisStatus;
import com.example.colis.model.Enums.ColisType;
import com.example.colis.model.Enums.Specialite;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ColisService {

    PageResponse<ColisResponse> getAllColis(int page, int size, ColisType type, ColisStatus statut);
    PageResponse<ColisResponse> getColisByTransporteur(String transporteurId, int page, int size, ColisType type, ColisStatus statut);
    PageResponse<ColisResponse> searchByDestination(String destination, int page, int size);
    PageResponse<ColisResponse> searchByDestinationForTransporteur(String transporteurId, String destination, int page, int size);
    ColisResponse createColis(ColisRequest request);
    ColisResponse assignColis(String colisId, AssignColisRequest request);
    ColisResponse updateColis(String id, ColisRequest request);
    ColisResponse updateStatus(String colisId, UpdateStatusRequest request, String userLogin);
    void deleteColis(String id);
}
