package com.example.colis.mapper;

import com.example.colis.dto.response.UserResponse;
import com.example.colis.model.Admin;
import com.example.colis.model.Transporteur;
import com.example.colis.model.Users;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponse toResponse(Users user) {
        if (user == null) {
            return null;
        }

        UserResponse.UserResponseBuilder builder = UserResponse.builder()
                .id(user.getId())
                .login(user.getLogin())
                .role(user.getRole())
                .active(user.isActive());

        if (user instanceof Transporteur) {
            Transporteur transporteur = (Transporteur) user;
            builder.specialite(transporteur.getSpecialite())
                    .status(transporteur.getStatus());
        }

        return builder.build();
    }


    public List<UserResponse> toResponseList(List<Users> users) {
        if (users == null) {
            return null;
        }
        return users.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public UserResponse toAdminResponse(Admin admin) {
        if (admin == null) {
            return null;
        }

        return UserResponse.builder()
                .id(admin.getId())
                .login(admin.getLogin())
                .role(admin.getRole())
                .active(admin.isActive())
                .build();
    }
}
