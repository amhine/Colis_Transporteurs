package com.example.colis.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.example.colis.model.Enums.Specialite;

@Data
@EqualsAndHashCode(callSuper=true)
public class FragileColis extends Colis{

    private String instructionsManutention;
    public FragileColis(){
        this.setType(Specialite.FRAGILE);
    }
}
