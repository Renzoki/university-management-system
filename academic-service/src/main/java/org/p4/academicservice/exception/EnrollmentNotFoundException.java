package org.p4.academicservice.exception;

import java.util.UUID;

public class EnrollmentNotFoundException extends RuntimeException {
    public EnrollmentNotFoundException(UUID enrollmentId) {
        super("Enrollment with id '" + enrollmentId + "' not found!");
    }
}
