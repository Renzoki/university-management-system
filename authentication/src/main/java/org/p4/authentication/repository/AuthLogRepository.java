package org.p4.authentication.repository;

import org.p4.authentication.model.entity.AuthLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthLogRepository extends JpaRepository<AuthLog, UUID> {
}
