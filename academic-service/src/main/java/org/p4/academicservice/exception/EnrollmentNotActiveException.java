package org.p4.academicservice.exception;

import java.util.UUID;

public class EnrollmentNotActiveException extends RuntimeException {
    public EnrollmentNotActiveException(UUID enrollmentId) {
        super("Enrollment with id '" + enrollmentId + "' is not active anymore!");
    }
}
