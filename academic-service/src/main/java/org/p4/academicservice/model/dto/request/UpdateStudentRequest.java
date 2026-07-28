package org.p4.academicservice.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.p4.academicservice.annotation.NullOrNotBlank;

public record UpdateStudentRequest(
        @NullOrNotBlank(message = FIRST_NAME_NULL_OR_NOT_BLANK)
        @Size(min = 1, max = 50, message = FIRST_NAME_LENGTH_ERROR)
        String firstName,

        @NullOrNotBlank(message = LAST_NAME_NULL_OR_NOT_BLANK)
        @Size(min = 1, max = 50, message = LAST_NAME_LENGTH_ERROR)
        String lastName,

        @Email(message = EMAIL_NOT_VALID_ERROR)
        @NullOrNotBlank(message = EMAIL_NULL_OR_NOT_BLANK)
        @Size(min = 1, max = 110, message = EMAIL_LENGTH_ERROR)
        String email
) {
    private static final String FIRST_NAME_NULL_OR_NOT_BLANK = "First name must be null or not blank!";
    private static final String FIRST_NAME_LENGTH_ERROR = "First name must be between 1 and 50 characters!";
    private static final String LAST_NAME_NULL_OR_NOT_BLANK = "Last name must be null or not blank!";
    private static final String LAST_NAME_LENGTH_ERROR = "Last name must be between 1 and 50 characters!";
    private static final String EMAIL_NOT_VALID_ERROR = "Invalid email provided!";
    private static final String EMAIL_NULL_OR_NOT_BLANK = "Email must be null or not blank!";
    private static final String EMAIL_LENGTH_ERROR = "Email must be between 1 and 110 characters!";
}