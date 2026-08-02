package org.p4.academicservice.repository;

import org.p4.academicservice.model.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GradeRepository extends JpaRepository<Grade, UUID> {
    Optional<Grade> findByEnrollmentId(UUID enrollmentId);
}
