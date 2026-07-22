package org.p4.authentication.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.p4.authentication.model.dto.LoginRequest;
import org.p4.authentication.model.dto.LoginResponse;
import org.p4.authentication.service.AuthService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> handleLogin(@Valid @RequestBody LoginRequest request){
        String accessToken = authService.authenticateUser(request);
        return new ResponseEntity<>(new LoginResponse(accessToken), HttpStatus.OK);
    }
}
