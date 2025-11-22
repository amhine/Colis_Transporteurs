package com.example.colis.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.example.colis.model.Enums.Role;
import com.example.colis.model.Enums.Specialite;
import com.example.colis.model.Enums.StatutTransporteur;

@Data
@EqualsAndHashCode(callSuper = true)
public class Transporteur extends User {
    private StatutTransporteur status;
    private Specialite specialite;

    public Transporteur(){
        this.setRole(Role.TRANSPORTEUR);
    }
}
