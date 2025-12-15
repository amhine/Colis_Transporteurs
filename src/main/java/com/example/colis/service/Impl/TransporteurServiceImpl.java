package com.example.colis.service.Impl;

import com.example.colis.dto.PageResponse;
import com.example.colis.dto.user.CreateUserRequest;
import com.example.colis.dto.user.UpdateUserRequest;
import com.example.colis.dto.user.UserResponse;
import com.example.colis.exception.BusinessException;
import com.example.colis.exception.ResourceNotFoundException;
import com.example.colis.mapper.UserMapper;
import com.example.colis.model.Enums.Role;
import com.example.colis.model.Enums.Specialite;
import com.example.colis.model.Transporteur;
import com.example.colis.model.User;
import com.example.colis.repository.UserRepository;
import com.example.colis.service.TransporteurService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransporteurServiceImpl implements TransporteurService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PageResponse<UserResponse> getAllTransporteurs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Transporteur> transporteurPage = userRepository.findAllTransporteurs(pageable);

        return PageResponse.<UserResponse>builder()
                .content(transporteurPage.getContent().stream()
                        .map(userMapper::toResponse)
                        .collect(Collectors.toList()))
                .pageNumber(transporteurPage.getNumber())
                .pageSize(transporteurPage.getSize())
                .totalElements(transporteurPage.getTotalElements())
                .totalPages(transporteurPage.getTotalPages())
                .last(transporteurPage.isLast())
                .build();
    }

    @Override
    public PageResponse<UserResponse> getTransporteursBySpecialite(Specialite specialite, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Transporteur> transporteurPage = userRepository.findTransporteursBySpecialite(specialite, pageable);

        return PageResponse.<UserResponse>builder()
                .content(transporteurPage.getContent().stream()
                        .map(userMapper::toResponse)
                        .collect(Collectors.toList()))
                .pageNumber(transporteurPage.getNumber())
                .pageSize(transporteurPage.getSize())
                .totalElements(transporteurPage.getTotalElements())
                .totalPages(transporteurPage.getTotalPages())
                .last(transporteurPage.isLast())
                .build();
    }

    @Override
    public UserResponse createTransporteur(CreateUserRequest request) {
        if (request.getRole() != Role.TRANSPORTEUR) {
            throw new BusinessException("Le rôle doit être TRANSPORTEUR");
        }

        if (userRepository.findByLogin(request.getLogin()).isPresent()) {
            throw new BusinessException("Un utilisateur avec ce login existe déjà");
        }

        if (request.getSpecialite() == null) {
            throw new BusinessException("La spécialité est obligatoire pour un transporteur");
        }

        if (request.getStatut() == null) {
            throw new BusinessException("Le statut est obligatoire pour un transporteur");
        }

        Transporteur transporteur = Transporteur.builder()
                .login(request.getLogin())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(true)
                .specialite(request.getSpecialite())
                .statut(request.getStatut())
                .build();

        Transporteur savedTransporteur = userRepository.save(transporteur);
        return userMapper.toResponse(savedTransporteur);
    }

    @Override
    public UserResponse updateTransporteur(String id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transporteur non trouvé"));

        if (!(user instanceof Transporteur)) {
            throw new BusinessException("Cet utilisateur n'est pas un transporteur");
        }

        Transporteur transporteur = (Transporteur) user;

        if (request.getLogin() != null && !request.getLogin().equals(transporteur.getLogin())) {
            if (userRepository.findByLogin(request.getLogin()).isPresent()) {
                throw new BusinessException("Un utilisateur avec ce login existe déjà");
            }
            transporteur.setLogin(request.getLogin());
        }

        if (request.getPassword() != null) {
            transporteur.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getActive() != null) {
            transporteur.setActive(request.getActive());
        }

        if (request.getStatut() != null) {
            transporteur.setStatut(request.getStatut());
        }

        if (request.getSpecialite() != null) {
            transporteur.setSpecialite(request.getSpecialite());
        }

        Transporteur updatedTransporteur = userRepository.save(transporteur);
        return userMapper.toResponse(updatedTransporteur);
    }

    @Override
    public void deleteTransporteur(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transporteur non trouvé"));

        if (!(user instanceof Transporteur)) {
            throw new BusinessException("Cet utilisateur n'est pas un transporteur");
        }

        userRepository.deleteById(id);
    }
}
