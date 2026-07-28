package org.p4.academicservice.repository;

import org.p4.academicservice.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
    boolean existsByEmail(String email);
}
