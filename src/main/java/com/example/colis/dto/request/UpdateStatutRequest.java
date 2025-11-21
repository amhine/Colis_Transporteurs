package com.example.colis.dto.request;

import com.example.colis.model.Enums.StatutColis;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatutRequest {
    @NotNull(message = "statut est obligatoire")
    private StatutColis statut;
}
