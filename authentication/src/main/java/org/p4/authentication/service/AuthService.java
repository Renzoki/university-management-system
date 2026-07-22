package org.p4.authentication.service;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.p4.authentication.exception.AuthenticationFailedException;
import org.p4.authentication.model.entity.User;
import org.p4.authentication.model.entity.UserStatus;
import org.p4.authentication.model.dto.LoginRequest;

@Service
public class AuthService {
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserService userService, BCryptPasswordEncoder passwordEncoder, JwtService jwtService){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String authenticateUser(LoginRequest request){
        User user = userService.getByEmail(request.email());

        if(user.getStatus() == UserStatus.SUSPENDED) {
            throw new AuthenticationFailedException();
        }

        if(!passwordEncoder.matches(request.password(), user.getPasswordHash())){
            throw new AuthenticationFailedException();
        }

        return jwtService.generateToken(user);
    }
}
