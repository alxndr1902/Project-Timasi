package com.zezame.timasi.service.impl;

import com.zezame.timasi.dto.role.RoleResponseDTO;
import com.zezame.timasi.exceptiohandler.exception.NotFoundException;
import com.zezame.timasi.model.company.Role;
import com.zezame.timasi.repository.RoleRepository;
import com.zezame.timasi.service.BaseService;
import com.zezame.timasi.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends BaseService implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleResponseDTO> getRoles() {
        List<Role> roles = roleRepository.findAll();
        List<RoleResponseDTO> dtos = roles.stream()
                .map(this::mapToDto)
                .toList();
        return dtos;
    }

    @Override
    public RoleResponseDTO getRole(String id) {
        var roleId = convertToUUID(id);
        var role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role Is Not Found"));
        RoleResponseDTO dto = mapToDto(role);
        return dto;
    }

    private RoleResponseDTO mapToDto(Role role) {
        RoleResponseDTO dto = new RoleResponseDTO(role.getId(), role.getCode(), role.getName());
        return dto;
    }
}
