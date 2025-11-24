package com.example.colis.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignColisRequest {
    @NotNull(message = "L'ID du transporteur est obligatoire")
    private String transporteurId;
}
