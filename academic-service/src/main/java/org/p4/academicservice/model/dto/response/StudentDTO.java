package org.p4.academicservice.model.dto.response;

import org.p4.academicservice.model.entity.enums.StudentStatus;

import java.util.UUID;

public record StudentDTO(
        UUID id,
        String firstName,
        String lastName,
        String email,
        StudentStatus status
) {
}
