package org.p4.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.p4.authentication.model.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
