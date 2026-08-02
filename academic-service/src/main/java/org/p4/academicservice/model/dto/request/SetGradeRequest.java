package org.p4.academicservice.model.dto.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record SetGradeRequest(
        @NotNull
        @DecimalMin("0.0")
        @DecimalMax("100.0")
        Double rawGrade
) {
}
