package com.example.colis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.colis.model.Enums.Specialite;
import com.example.colis.model.Enums.StatutColis;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "colis")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Colis {
    @Id
    private String id;
    private double poids;
    private String adresseDestination;
    private StatutColis statut;
    private String transporteurId;
    private Specialite type;
}
