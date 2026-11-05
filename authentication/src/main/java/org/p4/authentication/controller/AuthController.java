package org.p4.authentication.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.p4.authentication.model.entity.Action;
import org.p4.authentication.model.entity.AuthLog;
import org.p4.authentication.service.AuthLogService;
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
    private final AuthLogService logService;

    public AuthController(AuthService authService, AuthLogService logService){
        this.authService = authService;
        this.logService = logService;
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> handleLogin(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request){
        AuthLog log = logService.logEvent(loginRequest.email(), request.getRemoteAddr(), Action.LOGIN);
        String accessToken = authService.authenticateUser(loginRequest);
        logService.markAsSuccessful(log);
        return new ResponseEntity<>(new LoginResponse(accessToken), HttpStatus.OK);
    }
}
