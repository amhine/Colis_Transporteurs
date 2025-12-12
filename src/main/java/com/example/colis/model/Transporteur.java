package com.example.colis.model;

import com.example.colis.model.Enums.UserStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.example.colis.model.Enums.Specialite;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Transporteur extends User {
    @NotNull(message = "Statut est obligatoire pour un transporteur")
    private UserStatus statut;

    @NotNull(message = "Spécialité est obligatoire pour un transporteur")
    private Specialite specialite;
}
