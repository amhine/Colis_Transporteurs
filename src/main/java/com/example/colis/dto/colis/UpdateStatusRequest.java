package com.example.colis.dto.colis;

import com.example.colis.model.Enums.ColisStatus;
import com.example.colis.model.Enums.Specialite;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatusRequest {
    @NotNull(message = "Statut est obligatoire")
    private ColisStatus statut;

}
