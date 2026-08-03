package org.p4.academicservice.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record NewStudentRequest (
        @NotNull(message = NULL_ID_ERROR)
        UUID id,

        @NotBlank(message = FIRST_NAME_BLANK_ERROR)
        @Size(min = 1, max = 50, message = FIRST_NAME_LENGTH_ERROR)
        String firstName,

        @NotBlank(message = LAST_NAME_BLANK_ERROR)
        @Size(min = 1, max = 50, message = LAST_NAME_LENGTH_ERROR)
        String lastName,

        @Email(message = EMAIL_NOT_VALID_ERROR)
        @NotBlank(message = EMAIL_BLANK_ERROR)
        @Size(min = 1, max = 110, message = EMAIL_LENGTH_ERROR)
        String email
) {
    private static final String NULL_ID_ERROR = "ID is required!";
    private static final String FIRST_NAME_BLANK_ERROR = "First name is required!";
    private static final String FIRST_NAME_LENGTH_ERROR = "First name must be between 1 and 50 characters!";
    private static final String LAST_NAME_BLANK_ERROR = "Last name is required!";
    private static final String LAST_NAME_LENGTH_ERROR = "Last name must be between 1 and 50 characters!";
    private static final String EMAIL_NOT_VALID_ERROR = "Invalid email provided!";
    private static final String EMAIL_BLANK_ERROR = "Email is required!";
    private static final String EMAIL_LENGTH_ERROR = "Email must be between 1 and 110 characters!";
}
