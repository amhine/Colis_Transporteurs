package com.example.colis.config;

import com.example.colis.model.Admin;
import com.example.colis.model.Enums.Specialite;
import com.example.colis.model.Enums.UserStatus;
import com.example.colis.model.Transporteur;
import com.example.colis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {

            if (userRepository.count() > 0) {
                log.info("Des données existent déjà, initialisation ignorée.");
                return;
            }

            String adminLogin = System.getenv("INIT_ADMIN_LOGIN");
            String adminPassword = System.getenv("INIT_ADMIN_PASSWORD");

            if (adminLogin == null || adminPassword == null) {
                log.warn("Variables d'environnement admin manquantes, initialisation ignorée.");
                return;
            }

            Admin admin = Admin.builder()
                    .login(adminLogin)
                    .password(passwordEncoder.encode(adminPassword))
                    .active(true)
                    .build();

            userRepository.save(admin);
            log.info("Admin créé via variables d'environnement");

            createTransporteur(
                    "INIT_TRANS_STANDARD_LOGIN",
                    "INIT_TRANS_STANDARD_PASSWORD",
                    Specialite.STANDARD
            );

            createTransporteur(
                    "INIT_TRANS_FRAGILE_LOGIN",
                    "INIT_TRANS_FRAGILE_PASSWORD",
                    Specialite.FRAGILE
            );

            createTransporteur(
                    "INIT_TRANS_FRIGO_LOGIN",
                    "INIT_TRANS_FRIGO_PASSWORD",
                    Specialite.FRIGO
            );

            log.info("Initialisation DEV terminée");
        };
    }

    private void createTransporteur(String loginEnv, String passwordEnv, Specialite specialite) {
        String login = System.getenv(loginEnv);
        String password = System.getenv(passwordEnv);

        if (login == null || password == null) {
            log.warn("Transporteur {} non créé (env manquantes)", specialite);
            return;
        }

        Transporteur transporteur = Transporteur.builder()
                .login(login)
                .password(passwordEncoder.encode(password))
                .specialite(specialite)
                .statut(UserStatus.DISPONIBLE)
                .active(true)
                .build();

        userRepository.save(transporteur);
        log.info("Transporteur {} créé via env", specialite);
    }
}
