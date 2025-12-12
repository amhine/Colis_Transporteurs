package com.example.colis.mapper;

import com.example.colis.dto.user.UserResponse;
import com.example.colis.model.Admin;
import com.example.colis.model.Enums.Role;
import com.example.colis.model.Transporteur;
import com.example.colis.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        UserResponse.UserResponseBuilder builder = UserResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .active(user.getActive());

        if (user instanceof Admin) {
            builder.role(Role.ADMIN);
        } else if (user instanceof Transporteur) {
            Transporteur transporteur = (Transporteur) user;
            builder.role(Role.TRANSPORTEUR)
                    .statut(transporteur.getStatut())
                    .specialite(transporteur.getSpecialite());
        }

        return builder.build();
    }
}

