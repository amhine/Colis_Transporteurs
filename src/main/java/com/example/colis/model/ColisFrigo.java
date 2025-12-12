package com.example.colis.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class ColisFrigo extends Colis{

    @NotNull(message = "Température minimale est obligatoire pour un colis frigo")
    private Double temperatureMin;

    @NotNull(message = "Température maximale est obligatoire pour un colis frigo")
    private Double temperatureMax;
}
