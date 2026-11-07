package org.p4.authentication.service;

import org.p4.authentication.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.p4.authentication.exception.AuthenticationFailedException;
import org.p4.authentication.model.entity.User;
import org.p4.authentication.model.entity.UserStatus;
import org.p4.authentication.model.dto.LoginRequest;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder encoder, JwtService jwtService){
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    public String authenticateUser(LoginRequest request){
        User user = userRepository.findByEmail(request.email()).orElseThrow(AuthenticationFailedException::new);

        if(user.getStatus() == UserStatus.SUSPENDED) {
            throw new AuthenticationFailedException();
        }

        if(!encoder.matches(request.password(), user.getPasswordHash())){
            throw new AuthenticationFailedException();
        }

        return jwtService.generateToken(user);
    }
}
