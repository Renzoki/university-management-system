package org.p4.authentication.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record LoginRequest(
        @Email(message = INVALID_EMAIL)
        @NotBlank(message = BLANK_EMAIL)
        @Length(max = 255, message = EMAIL_LENGTH)
        String email,

        @NotBlank(message = BLANK_PASSWORD)
        String password
) {
    private final static String INVALID_EMAIL = "This is an invalid email!";
    private final static String BLANK_EMAIL = "Email cannot be blank!";
    private final static String EMAIL_LENGTH = "Email cannot exceed 255 characters!";
    private final static String BLANK_PASSWORD = "Password cannot be blank!";
}
