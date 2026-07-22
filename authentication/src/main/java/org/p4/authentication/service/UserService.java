package org.p4.authentication.service;

import org.springframework.stereotype.Service;
import org.p4.authentication.exception.AuthenticationFailedException;
import org.p4.authentication.model.entity.User;
import org.p4.authentication.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository){
        this.repository  = repository;
    }

    public User getByEmail(String email){
        return repository.findByEmail(email).orElseThrow(AuthenticationFailedException::new);
    }

}
