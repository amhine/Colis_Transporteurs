package com.example.colis.service.Impl;

import com.example.colis.mapper.UserMapper;
import com.example.colis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements AdminService {

    private final UserRepository adminRepository;
    private final UserMapper mapper;

    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return UserRepository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    public UserDTO reactivateUser(String id) {
        User user = UserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        user.setActive(true);
        return mapper.toDTO(userRepository.save(user));
    }
}
