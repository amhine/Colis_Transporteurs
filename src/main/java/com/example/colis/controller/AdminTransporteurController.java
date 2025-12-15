package com.example.colis.controller;


import com.example.colis.dto.PageResponse;
import com.example.colis.dto.user.CreateUserRequest;
import com.example.colis.dto.user.UpdateUserRequest;
import com.example.colis.dto.user.UserResponse;
import com.example.colis.model.Enums.Specialite;
import com.example.colis.service.TransporteurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/transporteurs")
@RequiredArgsConstructor
@Tag(name = "Admin - Transporteurs", description = "Gestion des transporteurs (ADMIN uniquement)")
@SecurityRequirement(name = "bearerAuth")
public class AdminTransporteurController {

    private final TransporteurService transporteurService;

    @GetMapping
    @Operation(summary = "Lister tous les transporteurs", description = "Récupère la liste paginée de tous les transporteurs")
    public ResponseEntity<PageResponse<UserResponse>> getAllTransporteurs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Specialite specialite) {

        PageResponse<UserResponse> response;
        if (specialite != null) {
            response = transporteurService.getTransporteursBySpecialite(specialite, page, size);
        } else {
            response = transporteurService.getAllTransporteurs(page, size);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Créer un transporteur", description = "Crée un nouveau transporteur")
    public ResponseEntity<UserResponse> createTransporteur(@Valid @RequestBody CreateUserRequest request) {
        UserResponse response = transporteurService.createTransporteur(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un transporteur", description = "Modifie un transporteur existant")
    public ResponseEntity<UserResponse> updateTransporteur(
            @PathVariable String id,
            @Valid @RequestBody UpdateUserRequest request) {
        UserResponse response = transporteurService.updateTransporteur(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un transporteur", description = "Supprime un transporteur")
    public ResponseEntity<Void> deleteTransporteur(@PathVariable String id) {
        transporteurService.deleteTransporteur(id);
        return ResponseEntity.noContent().build();
    }
}
