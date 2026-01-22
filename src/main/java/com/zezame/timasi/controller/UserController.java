package com.zezame.timasi.controller;

import com.zezame.timasi.dto.CreateResponseDTO;
import com.zezame.timasi.dto.CommonResponseDTO;
import com.zezame.timasi.dto.UpdateResponseDTO;
import com.zezame.timasi.dto.user.ChangePasswordRequestDTO;
import com.zezame.timasi.dto.user.UpdateUserRequestDTO;
import com.zezame.timasi.dto.user.UserRegisterRequestDTO;
import com.zezame.timasi.dto.user.UserResponseDTO;
import com.zezame.timasi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers(@RequestParam String roleCode) {
        List<UserResponseDTO> responses = userService.getUsers(roleCode);
        return new  ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable String id) {
        UserResponseDTO response = userService.getUser(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<CreateResponseDTO> register(@Valid @RequestBody UserRegisterRequestDTO request) {
        CreateResponseDTO response = userService.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<UpdateResponseDTO> updateUser(
            @PathVariable String id,
            @Valid @RequestBody UpdateUserRequestDTO request) {
        UpdateResponseDTO response = userService.updateUser(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CommonResponseDTO> delete(@PathVariable String id) {
        CommonResponseDTO response = userService.deleteUser(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("change-password")
    public ResponseEntity<UpdateResponseDTO> changePassword(@Valid @RequestBody ChangePasswordRequestDTO request) {
        UpdateResponseDTO response = userService.changePassword(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
