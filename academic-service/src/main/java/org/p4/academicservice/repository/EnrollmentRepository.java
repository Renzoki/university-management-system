package org.p4.academicservice.repository;

import org.p4.academicservice.model.entity.Enrollment;
import org.p4.academicservice.model.entity.Grade;
import org.p4.academicservice.model.entity.enums.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EnrollmentRepository extends JpaRepository<Enrollment, UUID> {
    @Query("SELECT e.grade FROM Enrollment e WHERE e.student.id = :studentId AND e.course.id = :courseId")
    Optional<Grade> findGradeByStudentIdAndCourseId(
            @Param("studentId") UUID studentId,
            @Param("courseId") UUID courseId
    );

    Optional<Enrollment> findByStudentIdAndCourseId(UUID studentId, UUID courseId);
    boolean existsByCourseIdAndStatus(UUID courseId, EnrollmentStatus status);
    List<Enrollment> findByCourseId(UUID id);
    List<Enrollment> findByCourse_CourseCode(String courseCode);
}
