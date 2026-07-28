package org.p4.academicservice.repository;

import org.p4.academicservice.model.entity.Enrollment;
import org.p4.academicservice.model.entity.enums.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    boolean existsByCourseIdAndStatus(UUID courseId, EnrollmentStatus status);
    List<Enrollment> findByCourseId(UUID id);
    List<Enrollment> findByCourseCode(String courseCode);

}
