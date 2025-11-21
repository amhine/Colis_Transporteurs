package com.example.colis.service.Impl;

import com.example.colis.mapper.UserMapper;
import com.example.colis.model.Enums.Role;
import com.example.colis.model.Enums.Specialite;
import com.example.colis.service.TransporteurService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransporteurServiceImpl implements TransporteurService {

    private final TransporteurRepository transporteurRepository;
    private final UserMapper mapper;

    @Override
    public Page<UserDTO> getAllTransporteurs(Pageable pageable) {
        return transporteurRepository
                .findByRole(Role.TRANSPORTEUR, pageable)
                .map(mapper::toDTO);
    }

    @Override
    public Page<UserDTO> getTransporteursBySpecialite(Specialite specialite, Pageable pageable) {
        return transporteurRepository
                .findByRoleAndSpecialite(Role.TRANSPORTEUR, specialite, pageable)
                .map(mapper::toDTO);
    }

    @Override
    public UserDTO createTransporteur(UserDTO dto) {
        User entity = mapper.toEntity(dto);
        entity.setRole(Role.TRANSPORTEUR);
        return mapper.toDTO(transporteurRepository.save(entity));
    }

    @Override
    public UserDTO updateTransporteur(String id, UserDTO dto) {
        User user = transporteurRepository.findByIdAndRole(id, Role.TRANSPORTEUR)
                .orElseThrow(() -> new RuntimeException("Transporteur introuvable"));

        mapper.updateEntity(user, dto);
        return mapper.toDTO(transporteurRepository.save(user));
    }

    @Override
    public void deleteTransporteur(String id) {
        transporteurRepository.deleteById(id);
    }

}
