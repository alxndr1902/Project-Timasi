package com.zezame.timasi.controller;

import com.zezame.timasi.dto.role.RoleResponseDTO;
import com.zezame.timasi.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDTO>> getRoles() {
        List<RoleResponseDTO> responses = roleService.getRoles();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<RoleResponseDTO> getRole(@PathVariable String id) {
        RoleResponseDTO response = roleService.getRole(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
