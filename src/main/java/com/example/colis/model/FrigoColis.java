package com.example.colis.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.example.colis.model.Enums.Specialite;

@Data
@EqualsAndHashCode(callSuper=true)
public class FrigoColis extends Colis{
    private double temperatureMin;
    private double temperatureMax;

    public FrigoColis(){
        this.setType(Specialite.FRIGO);
    }
}
