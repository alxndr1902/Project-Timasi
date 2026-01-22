package com.zezame.timasi.service;

import com.zezame.timasi.dto.role.RoleResponseDTO;

import java.util.List;

public interface RoleService {
    List<RoleResponseDTO> getRoles();

    RoleResponseDTO getRole(String id);
}
