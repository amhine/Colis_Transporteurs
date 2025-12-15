package com.example.colis.service.Impl;

import com.example.colis.dto.PageResponse;
import com.example.colis.dto.user.CreateUserRequest;
import com.example.colis.dto.user.UpdateUserRequest;
import com.example.colis.dto.user.UserResponse;
import com.example.colis.exception.BusinessException;
import com.example.colis.exception.ResourceNotFoundException;
import com.example.colis.mapper.UserMapper;
import com.example.colis.model.Admin;
import com.example.colis.model.Enums.Role;
import com.example.colis.model.Transporteur;
import com.example.colis.model.User;
import com.example.colis.repository.UserRepository;
import com.example.colis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public PageResponse<UserResponse> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);

        return PageResponse.<UserResponse>builder()
                .content(userPage.getContent().stream()
                        .map(userMapper::toResponse)
                        .collect(Collectors.toList()))
                .pageNumber(userPage.getNumber())
                .pageSize(userPage.getSize())
                .totalElements(userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .last(userPage.isLast())
                .build();
    }
    @Override
    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.findByLogin(request.getLogin()).isPresent()) {
            throw new BusinessException("Un utilisateur avec ce login existe déjà");
        }

        User user;
        if (request.getRole() == Role.ADMIN) {
            user = Admin.builder()
                    .login(request.getLogin())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .active(true)
                    .build();
        } else {
            if (request.getSpecialite() == null) {
                throw new BusinessException("La spécialité est obligatoire pour un transporteur");
            }
            if (request.getStatut() == null) {
                throw new BusinessException("Le statut est obligatoire pour un transporteur");
            }
            user = Transporteur.builder()
                    .login(request.getLogin())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .active(true)
                    .specialite(request.getSpecialite())
                    .statut(request.getStatut())
                    .build();
        }

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse updateUser(String id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        if (request.getLogin() != null && !request.getLogin().equals(user.getLogin())) {
            if (userRepository.findByLogin(request.getLogin()).isPresent()) {
                throw new BusinessException("Un utilisateur avec ce login existe déjà");
            }
            user.setLogin(request.getLogin());
        }

        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getActive() != null) {
            user.setActive(request.getActive());
        }

        if (user instanceof Transporteur) {
            Transporteur transporteur = (Transporteur) user;
            if (request.getStatut() != null) {
                transporteur.setStatut(request.getStatut());
            }
            if (request.getSpecialite() != null) {
                transporteur.setSpecialite(request.getSpecialite());
            }
        }

        User updatedUser = userRepository.save(user);
        return userMapper.toResponse(updatedUser);
    }

    @Override
    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Utilisateur non trouvé");
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserResponse activateUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        user.setActive(true);
        User updatedUser = userRepository.save(user);
        return userMapper.toResponse(updatedUser);
    }

    @Override
    public UserResponse deactivateUser(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
        user.setActive(false);
        User updatedUser = userRepository.save(user);
        return userMapper.toResponse(updatedUser);
    }
}
