package com.zezame.timasi.controller;

import com.zezame.timasi.dto.CreateResponseDTO;
import com.zezame.timasi.dto.DeleteResponseDTO;
import com.zezame.timasi.dto.UpdateResponseDTO;
import com.zezame.timasi.dto.user.ChangePasswordRequestDTO;
import com.zezame.timasi.dto.user.UpdateUserRequestDTO;
import com.zezame.timasi.dto.user.UserRegisterRequestDTO;
import com.zezame.timasi.dto.user.UserResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @GetMapping
    public ResponseEntity<UserResponseDTO> getUsers(@RequestParam(required = false) String roleCode) {
        return null;
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable String id) {
        return null;
    }

    @PostMapping("register")
    public ResponseEntity<CreateResponseDTO> register(@Valid @RequestBody UserRegisterRequestDTO request) {
        return null;
    }

    @PutMapping("{id}")
    public ResponseEntity<UpdateResponseDTO> updateUser(
            @PathVariable String id,
            @Valid @RequestBody UpdateUserRequestDTO request) {
        return null;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DeleteResponseDTO> delete(@PathVariable String id) {
        return null;
    }

    @PatchMapping("change-password")
    public ResponseEntity<UpdateResponseDTO> changePassword(@Valid @RequestBody ChangePasswordRequestDTO request) {
        return null;
    }

    @PostMapping("assignee/{id}")
    public ResponseEntity<UpdateResponseDTO> assignCustomer(@PathVariable String id,
                                                            List<String> customerIds) {
        return null;
    }
}
