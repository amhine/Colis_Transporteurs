package com.example.colis.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.example.colis.model.Enums.Specialite;

@Data
@EqualsAndHashCode(callSuper=true)
public class StandardColis extends Colis{

    public StandardColis(){
        this.setType(Specialite.STANDARD);
    }
}
