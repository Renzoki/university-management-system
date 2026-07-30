package org.p4.academicservice.model.dto.response;

import java.util.UUID;

public record GradeDTO(
        UUID gradeId,
        double rawGrade,
        double gradeEquivalent
) {
}
