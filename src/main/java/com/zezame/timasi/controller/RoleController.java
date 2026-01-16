package com.zezame.timasi.controller;

import com.zezame.timasi.dto.role.RoleResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("roles")
public class RoleController {
    @GetMapping
    public ResponseEntity<RoleResponseDTO> getRoles() {
        return null;
    }

    @GetMapping("{id}")
    public ResponseEntity<RoleResponseDTO> getRole(@PathVariable String id) {
        return null;
    }
}
