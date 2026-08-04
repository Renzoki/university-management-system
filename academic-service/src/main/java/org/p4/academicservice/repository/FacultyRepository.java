package org.p4.academicservice.repository;

import org.p4.academicservice.model.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FacultyRepository extends JpaRepository<Faculty, UUID> {
    boolean existsByEmail(String email);
}
