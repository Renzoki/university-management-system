package org.p4.academicservice.model.dto.response;

import org.p4.academicservice.model.entity.enums.CourseStatus;

import java.util.UUID;

public record CourseDTO(
        UUID id,
        String name,
        String courseCode,
        CourseStatus status,
        FacultyDTO faculty
) {
}
