package com.zezame.timasi.service.impl;

import com.zezame.timasi.dto.role.RoleResponseDTO;
import com.zezame.timasi.exceptiohandler.exception.NotFoundException;
import com.zezame.timasi.model.company.Role;
import com.zezame.timasi.repository.RoleRepository;
import com.zezame.timasi.service.BaseService;
import com.zezame.timasi.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoleServiceImpl extends BaseService implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleResponseDTO> getRoles() {
        List<Role> roles = roleRepository.findAll();
        List<RoleResponseDTO> responses = roles.stream()
                .map(this::mapToDto)
                .toList();
        return responses;
    }

    @Override
    public RoleResponseDTO getRole(String id) {
        UUID roleId = convertToUUID(id);
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role Is Not Found"));
        RoleResponseDTO response = mapToDto(role);
        return response;
    }

    private RoleResponseDTO mapToDto(Role role) {
        RoleResponseDTO dto = new RoleResponseDTO(role.getId(), role.getCode(), role.getName());
        return dto;
    }
}
