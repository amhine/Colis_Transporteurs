package com.example.colis.service.Impl;

import com.example.colis.dto.PageResponse;
import com.example.colis.dto.colis.AssignColisRequest;
import com.example.colis.dto.colis.ColisRequest;
import com.example.colis.dto.colis.ColisResponse;
import com.example.colis.dto.colis.UpdateStatusRequest;
import com.example.colis.exception.BusinessException;
import com.example.colis.exception.ResourceNotFoundException;
import com.example.colis.exception.UnauthorizedException;
import com.example.colis.mapper.ColisMapper;
import com.example.colis.model.*;
import com.example.colis.model.Enums.ColisStatus;
import com.example.colis.model.Enums.ColisType;
import com.example.colis.model.Enums.Specialite;
import com.example.colis.repository.ColisRepository;
import com.example.colis.repository.UserRepository;
import com.example.colis.service.ColisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.example.colis.model.Enums.Specialite.FRIGO;

@Service
@RequiredArgsConstructor
public class ColisServiceImpl implements ColisService {
    private final ColisRepository colisRepository;
    private final UserRepository userRepository;
    private final ColisMapper colisMapper;

    @Override
    public PageResponse<ColisResponse> getAllColis(int page, int size, ColisType type, ColisStatus statut) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Colis> colisPage;

        if (type != null && statut != null) {
            String className = getClassNameForType(type);
            colisPage = colisRepository.findByClassAndStatut(className, statut, pageable);
        } else if (type != null) {
            String className = getClassNameForType(type);
            colisPage = colisRepository.findByClass(className, pageable);
        } else if (statut != null) {
            colisPage = colisRepository.findByStatut(statut, pageable);
        } else {
            colisPage = colisRepository.findAll(pageable);
        }

        return buildPageResponse(colisPage);
    }

    @Override
    public PageResponse<ColisResponse> getColisByTransporteur(String transporteurId, int page, int size,
                                                              ColisType type, ColisStatus statut) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Colis> colisPage;

        if (type != null && statut != null) {
            String className = getClassNameForType(type);
            colisPage = colisRepository.findByTransporteurIdAndClassAndStatut(transporteurId, className, statut, pageable);
        } else if (type != null) {
            String className = getClassNameForType(type);
            colisPage = colisRepository.findByTransporteurIdAndClass(transporteurId, className, pageable);
        } else if (statut != null) {
            colisPage = colisRepository.findByTransporteurIdAndStatut(transporteurId, statut, pageable);
        } else {
            colisPage = colisRepository.findByTransporteurId(transporteurId, pageable);
        }

        return buildPageResponse(colisPage);
    }

    @Override
    public PageResponse<ColisResponse> searchByDestination(String destination, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Colis> colisPage = colisRepository.findByAdresseDestinationContainingIgnoreCase(destination, pageable);
        return buildPageResponse(colisPage);
    }

    @Override
    public PageResponse<ColisResponse> searchByDestinationForTransporteur(String transporteurId,
                                                                          String destination,
                                                                          int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Colis> colisPage = colisRepository.findByTransporteurIdAndAdresseDestinationContainingIgnoreCase(
                transporteurId, destination, pageable);
        return buildPageResponse(colisPage);
    }

    @Override
    public ColisResponse createColis(ColisRequest request) {
        Colis colis = createColisInstance(request);
        colis.setStatut(ColisStatus.EN_ATTENTE);

        Colis savedColis = colisRepository.save(colis);
        return colisMapper.toResponse(savedColis);
    }

    private Colis createColisInstance(ColisRequest request) {
        switch (request.getType()) {
            case STANDARD:
                return ColisStandard.builder()
                        .poids(request.getPoids())
                        .adresseDestination(request.getAdresseDestination())
                        .statut(ColisStatus.EN_ATTENTE)
                        .build();

            case FRAGILE:
                if (request.getInstructionsManutention() == null || request.getInstructionsManutention().isBlank()) {
                    throw new BusinessException("Les instructions de manutention sont obligatoires pour un colis FRAGILE");
                }
                return ColisFragile.builder()
                        .poids(request.getPoids())
                        .adresseDestination(request.getAdresseDestination())
                        .statut(ColisStatus.EN_ATTENTE)
                        .instructionsManutention(request.getInstructionsManutention())
                        .build();

            case FRIGO:
                if (request.getTemperatureMin() == null || request.getTemperatureMax() == null) {
                    throw new BusinessException("Les températures min et max sont obligatoires pour un colis FRIGO");
                }
                if (request.getTemperatureMin() >= request.getTemperatureMax()) {
                    throw new BusinessException("La température min doit être inférieure à la température max");
                }
                return ColisFrigo.builder()
                        .poids(request.getPoids())
                        .adresseDestination(request.getAdresseDestination())
                        .statut(ColisStatus.EN_ATTENTE)
                        .temperatureMin(request.getTemperatureMin())
                        .temperatureMax(request.getTemperatureMax())
                        .build();

            default:
                throw new BusinessException("Type de colis non supporté");
        }
    }


    @Override
    public ColisResponse assignColis(String colisId, AssignColisRequest request) {
        Colis colis = colisRepository.findById(colisId)
                .orElseThrow(() -> new ResourceNotFoundException("Colis non trouvé"));

        User user = userRepository.findById(request.getTransporteurId())
                .orElseThrow(() -> new ResourceNotFoundException("Transporteur non trouvé"));

        if (!(user instanceof Transporteur)) {
            throw new BusinessException("L'utilisateur n'est pas un transporteur");
        }

        Transporteur transporteur = (Transporteur) user;

        if (!transporteur.getActive()) {
            throw new BusinessException("Le transporteur est désactivé");
        }

        Specialite requiredSpecialite = getRequiredSpecialite(colis);
        if (transporteur.getSpecialite() != requiredSpecialite) {
            throw new BusinessException(
                    String.format("Le transporteur doit avoir la spécialité %s pour ce type de colis",
                            requiredSpecialite));
        }

        colis.setTransporteurId(request.getTransporteurId());
        colis.setStatut(ColisStatus.EN_TRANSIT);

        Colis updatedColis = colisRepository.save(colis);
        return colisMapper.toResponse(updatedColis);
    }

    @Override
    public ColisResponse updateColis(String id, ColisRequest request) {
        Colis colis = colisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colis non trouvé"));

        // Mettre à jour les champs communs
        colis.setPoids(request.getPoids());
        colis.setAdresseDestination(request.getAdresseDestination());

        // Mettre à jour les champs spécifiques selon le type
        if (colis instanceof ColisFragile) {
            if (request.getInstructionsManutention() == null || request.getInstructionsManutention().isBlank()) {
                throw new BusinessException("Les instructions de manutention sont obligatoires pour un colis FRAGILE");
            }
            ((ColisFragile) colis).setInstructionsManutention(request.getInstructionsManutention());
        } else if (colis instanceof ColisFrigo) {
            if (request.getTemperatureMin() == null || request.getTemperatureMax() == null) {
                throw new BusinessException("Les températures min et max sont obligatoires pour un colis FRIGO");
            }
            if (request.getTemperatureMin() >= request.getTemperatureMax()) {
                throw new BusinessException("La température min doit être inférieure à la température max");
            }
            ((ColisFrigo) colis).setTemperatureMin(request.getTemperatureMin());
            ((ColisFrigo) colis).setTemperatureMax(request.getTemperatureMax());
        }

        Colis updatedColis = colisRepository.save(colis);
        return colisMapper.toResponse(updatedColis);
    }

    @Override
    public ColisResponse updateStatus(String colisId, UpdateStatusRequest request, String userLogin) {
        Colis colis = colisRepository.findById(colisId)
                .orElseThrow(() -> new ResourceNotFoundException("Colis non trouvé"));

        User user = userRepository.findByLogin(userLogin)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        if (user instanceof Transporteur) {
            if (colis.getTransporteurId() == null || !colis.getTransporteurId().equals(user.getId())) {
                throw new UnauthorizedException("Vous n'êtes pas autorisé à modifier ce colis");
            }
        }

        colis.setStatut(request.getStatut());

        Colis updatedColis = colisRepository.save(colis);
        return colisMapper.toResponse(updatedColis);
    }

    @Override
    public void deleteColis(String id) {
        if (!colisRepository.existsById(id)) {
            throw new ResourceNotFoundException("Colis non trouvé");
        }
        colisRepository.deleteById(id);
    }

    private Specialite getRequiredSpecialite(Colis colis) {
        if (colis instanceof ColisStandard) {
            return Specialite.STANDARD;
        } else if (colis instanceof ColisFragile) {
            return Specialite.FRAGILE;
        } else if (colis instanceof ColisFrigo) {
            return Specialite.FRIGO;
        }
        throw new BusinessException("Type de colis non reconnu");
    }

    private String getClassNameForType(ColisType type) {
        switch (type) {
            case STANDARD:
                return "com.transporteur.model.ColisStandard";
            case FRAGILE:
                return "com.transporteur.model.ColisFragile";
            case FRIGO:
                return "com.transporteur.model.ColisFrigo";
            default:
                throw new BusinessException("Type de colis non supporté");
        }
    }
    private PageResponse<ColisResponse> buildPageResponse(Page<Colis> colisPage) {
        return PageResponse.<ColisResponse>builder()
                .content(colisPage.getContent().stream()
                        .map(colisMapper::toResponse)
                        .collect(Collectors.toList()))
                .pageNumber(colisPage.getNumber())
                .pageSize(colisPage.getSize())
                .totalElements(colisPage.getTotalElements())
                .totalPages(colisPage.getTotalPages())
                .last(colisPage.isLast())
                .build();
    }

}
