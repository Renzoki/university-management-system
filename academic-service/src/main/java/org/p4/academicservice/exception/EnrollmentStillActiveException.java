package org.p4.academicservice.exception;

import java.util.UUID;

public class EnrollmentStillActiveException extends RuntimeException {
    public EnrollmentStillActiveException(UUID enrollmentId) {
        super("Enrollment with id '" + enrollmentId + "' is still active and cannot be deleted!");
    }
}
