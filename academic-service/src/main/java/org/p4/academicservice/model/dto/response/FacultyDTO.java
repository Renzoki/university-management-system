package org.p4.academicservice.model.dto.response;

import org.p4.academicservice.model.entity.enums.FacultyStatus;

import java.util.UUID;

public record FacultyDTO(
        UUID id,
        String firstName,
        String lastName,
        String email,
        FacultyStatus status
) {
}
