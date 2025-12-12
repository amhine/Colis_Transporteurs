package com.example.colis.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class ColisFragile extends Colis{

    @NotBlank(message = "Instructions de manutention sont obligatoires pour un colis fragile")
    private String instructionsManutention;

   }
