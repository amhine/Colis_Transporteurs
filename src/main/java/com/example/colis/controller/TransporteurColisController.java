package com.example.colis.controller;

import com.example.colis.dto.PageResponse;
import com.example.colis.dto.colis.ColisResponse;
import com.example.colis.dto.colis.UpdateStatusRequest;
import com.example.colis.model.Enums.ColisStatus;
import com.example.colis.model.Enums.ColisType;
import com.example.colis.model.User;
import com.example.colis.repository.UserRepository;
import com.example.colis.service.ColisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transporteur/colis")
@RequiredArgsConstructor
@Tag(name = "Transporteur - Colis", description = "Gestion des colis (TRANSPORTEUR uniquement)")
@SecurityRequirement(name = "bearerAuth")
public class TransporteurColisController {

    private final ColisService colisService;
    private final UserRepository userRepository;

    @GetMapping
    @Operation(summary = "Lister mes colis", description = "Récupère la liste paginée des colis assignés au transporteur connecté")
    public ResponseEntity<PageResponse<ColisResponse>> getMyColis(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) ColisType type,
            @RequestParam(required = false) ColisStatus statut,
            Authentication authentication) {

        String login = authentication.getName();
        User transporteur = userRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        PageResponse<ColisResponse> response = colisService.getColisByTransporteur(
                transporteur.getId(), page, size, type, statut);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher mes colis par destination",
            description = "Recherche des colis assignés au transporteur par adresse de destination")
    public ResponseEntity<PageResponse<ColisResponse>> searchMyColisByDestination(
            @RequestParam String destination,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {

        String login = authentication.getName();
        User transporteur = userRepository.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        PageResponse<ColisResponse> response = colisService.searchByDestinationForTransporteur(
                transporteur.getId(), destination, page, size);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Mettre à jour le statut d'un colis",
            description = "Modifie le statut d'un colis assigné au transporteur")
    public ResponseEntity<ColisResponse> updateStatus(
            @PathVariable String id,
            @Valid @RequestBody UpdateStatusRequest request,
            Authentication authentication) {
        ColisResponse response = colisService.updateStatus(id, request, authentication.getName());
        return ResponseEntity.ok(response);
    }
}
