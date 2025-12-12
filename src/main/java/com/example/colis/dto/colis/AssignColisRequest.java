package com.example.colis.dto.colis;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignColisRequest {

    @NotBlank(message = "ID du transporteur est obligatoire")
    private String transporteurId;
}
