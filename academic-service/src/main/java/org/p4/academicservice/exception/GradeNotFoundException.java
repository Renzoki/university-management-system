package org.p4.academicservice.exception;

import java.util.UUID;

public class GradeNotFoundException extends RuntimeException {
    public GradeNotFoundException(UUID enrollmentId) {
        super("Grade for enrollment with id'" + enrollmentId + "' not found!");
    }
}
