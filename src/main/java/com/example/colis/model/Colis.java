package com.example.colis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.colis.model.Enums.Specialite;
import com.example.colis.model.Enums.StatutColis;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "colis")
public class Colis {
    @Id
    private long id;
    private double poids;
    private String adresseDestination;
    private StatutColis statut;
    private String TransporteurId;
    private Specialite type;
}
