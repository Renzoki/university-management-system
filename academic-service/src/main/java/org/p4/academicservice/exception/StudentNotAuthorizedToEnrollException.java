package org.p4.academicservice.exception;

import java.util.UUID;

public class StudentNotAuthorizedToEnrollException extends RuntimeException {
    public StudentNotAuthorizedToEnrollException(UUID studentId) {
        super("Student with id '" + studentId + "' is only allowed to drop courses they are enrolled in!");
    }
}
