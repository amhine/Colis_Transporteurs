package com.example.colis.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.colis.model.Enums.Role;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @NotBlank(message = "Login est obligatoire")
    @Indexed(unique = true)
    private String login;

    @NotBlank(message = "Password est obligatoire")
    private String password;

    @NotNull(message = "Active est obligatoire")
    @Builder.Default
    private Boolean active = true;
}
