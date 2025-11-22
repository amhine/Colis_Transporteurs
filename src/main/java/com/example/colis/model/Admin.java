package com.example.colis.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.example.colis.model.Enums.Role;

@Data
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {

    public Admin(){
        this.setRole(Role.ADMIN);
    }
}
