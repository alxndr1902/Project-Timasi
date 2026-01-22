package com.zezame.timasi.controller;

import com.zezame.timasi.dto.auth.LoginRequestDTO;
import com.zezame.timasi.dto.auth.LoginResponseDTO;
import com.zezame.timasi.model.company.User;
import com.zezame.timasi.service.UserService;
import com.zezame.timasi.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request) {
        var auth = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        authenticationManager.authenticate(auth);

        User user = userService.findByEmail(request.getEmail());

        var token = jwtService.generateToken(user.getId().toString());
        return new ResponseEntity<>(new LoginResponseDTO(user.getFullName(),
                user.getRole().getCode(), token), HttpStatus.OK);
    }
}
