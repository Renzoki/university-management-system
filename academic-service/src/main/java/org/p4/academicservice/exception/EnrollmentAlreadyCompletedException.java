package org.p4.academicservice.exception;

import java.util.UUID;

public class EnrollmentAlreadyCompletedException extends RuntimeException {
    public EnrollmentAlreadyCompletedException(UUID enrollmentId) {
        super("Enrollment with id '" + enrollmentId + "' is already completed and cannot be dropped!");
    }
}
