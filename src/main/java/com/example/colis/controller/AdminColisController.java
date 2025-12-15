package com.example.colis.controller;

import com.example.colis.dto.PageResponse;
import com.example.colis.dto.colis.AssignColisRequest;
import com.example.colis.dto.colis.ColisRequest;
import com.example.colis.dto.colis.ColisResponse;
import com.example.colis.dto.colis.UpdateStatusRequest;
import com.example.colis.model.Enums.ColisStatus;
import com.example.colis.model.Enums.ColisType;
import com.example.colis.service.ColisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/colis")
@RequiredArgsConstructor
@Tag(name = "Admin - Colis", description = "Gestion des colis (ADMIN uniquement)")
@SecurityRequirement(name = "bearerAuth")
public class AdminColisController {

    private final ColisService colisService;

    @GetMapping
    @Operation(summary = "Lister tous les colis", description = "Récupère la liste paginée de tous les colis avec filtres optionnels")
    public ResponseEntity<PageResponse<ColisResponse>> getAllColis(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) ColisType type,
            @RequestParam(required = false) ColisStatus statut) {
        PageResponse<ColisResponse> response = colisService.getAllColis(page, size, type, statut);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher des colis par destination", description = "Recherche des colis par adresse de destination")
    public ResponseEntity<PageResponse<ColisResponse>> searchByDestination(
            @RequestParam String destination,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<ColisResponse> response = colisService.searchByDestination(destination, page, size);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Créer un colis", description = "Crée un nouveau colis")
    public ResponseEntity<ColisResponse> createColis(@Valid @RequestBody ColisRequest request) {
        ColisResponse response = colisService.createColis(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un colis", description = "Modifie un colis existant")
    public ResponseEntity<ColisResponse> updateColis(
            @PathVariable String id,
            @Valid @RequestBody ColisRequest request) {
        ColisResponse response = colisService.updateColis(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/assign")
    @Operation(summary = "Assigner un colis à un transporteur",
            description = "Assigne un colis à un transporteur (vérifie la correspondance spécialité/type)")
    public ResponseEntity<ColisResponse> assignColis(
            @PathVariable String id,
            @Valid @RequestBody AssignColisRequest request) {
        ColisResponse response = colisService.assignColis(id, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Mettre à jour le statut d'un colis", description = "Modifie le statut d'un colis")
    public ResponseEntity<ColisResponse> updateStatus(
            @PathVariable String id,
            @Valid @RequestBody UpdateStatusRequest request,
            Authentication authentication) {
        ColisResponse response = colisService.updateStatus(id, request, authentication.getName());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un colis", description = "Supprime un colis")
    public ResponseEntity<Void> deleteColis(@PathVariable String id) {
        colisService.deleteColis(id);
        return ResponseEntity.noContent().build();
    }
}
