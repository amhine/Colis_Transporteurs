package com.example.colis.controller;

import com.example.colis.dto.PageResponse;
import com.example.colis.dto.user.CreateUserRequest;
import com.example.colis.dto.user.UpdateUserRequest;
import com.example.colis.dto.user.UserResponse;
import com.example.colis.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@Tag(name = "Admin - Users", description = "Gestion des utilisateurs (ADMIN uniquement)")
@SecurityRequirement(name = "bearerAuth")
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Lister tous les utilisateurs", description = "Récupère la liste paginée de tous les utilisateurs")
    public ResponseEntity<PageResponse<UserResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageResponse<UserResponse> response = userService.getAllUsers(page, size);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Créer un utilisateur", description = "Crée un nouvel utilisateur (ADMIN ou TRANSPORTEUR)")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifier un utilisateur", description = "Modifie un utilisateur existant (le rôle ne peut pas être modifié)")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable String id,
            @Valid @RequestBody UpdateUserRequest request) {
        UserResponse response = userService.updateUser(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un utilisateur", description = "Supprime un utilisateur")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/activate")
    @Operation(summary = "Activer un utilisateur", description = "Active un compte utilisateur désactivé")
    public ResponseEntity<UserResponse> activateUser(@PathVariable String id) {
        UserResponse response = userService.activateUser(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/deactivate")
    @Operation(summary = "Désactiver un utilisateur", description = "Désactive un compte utilisateur")
    public ResponseEntity<UserResponse> deactivateUser(@PathVariable String id) {
        UserResponse response = userService.deactivateUser(id);
        return ResponseEntity.ok(response);
    }
}
