package com.example.colis.model;

import com.example.colis.model.Enums.ColisStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "colis")
public class Colis {
    @Id
    private String id;
    @NotNull(message = "Poids est obligatoire")
    @Positive(message = "Poids doit être positif")
    private Double poids;
    @NotBlank(message = "Adresse de destination est obligatoire")
    private String adresseDestination;
    @NotNull(message = "Statut est obligatoire")
    @Builder.Default
    private ColisStatus statut = ColisStatus.EN_ATTENTE;

    private String transporteurId;
}
