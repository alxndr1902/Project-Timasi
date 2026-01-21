package com.zezame.timasi.controller;

import com.zezame.timasi.dto.auth.LoginRequestDTO;
import com.zezame.timasi.dto.auth.LoginResponseDTO;
import com.zezame.timasi.model.company.User;
import com.zezame.timasi.service.UserService;
import com.zezame.timasi.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request) {
        var auth = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        authenticationManager.authenticate(auth);

        User user = userService.findByEmail(request.getEmail());

        var token = JwtUtil.generateToken(user.getId().toString(),
                Timestamp.valueOf(LocalDateTime.now().plusHours(2)));
        return new ResponseEntity<>(new LoginResponseDTO(user.getFullName(),
                user.getRole().getCode(), token), HttpStatus.OK);
    }
}
