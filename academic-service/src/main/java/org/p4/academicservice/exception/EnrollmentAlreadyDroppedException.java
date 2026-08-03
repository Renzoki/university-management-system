package org.p4.academicservice.exception;

import java.util.UUID;

public class EnrollmentAlreadyDroppedException extends RuntimeException {
    public EnrollmentAlreadyDroppedException(UUID enrollmentId) {
        super("Enrollment with id '" + enrollmentId + "' is already dropped and cannot be dropped!");
    }
}
