package org.p4.academicservice.model.dto.response;

import org.p4.academicservice.model.entity.enums.EnrollmentStatus;

import java.util.UUID;

public record EnrollmentDTO(
        UUID enrollmentId,
        CourseDTO course,
        StudentDTO student,
        GradeDTO grade,
        EnrollmentStatus status
) {
}
