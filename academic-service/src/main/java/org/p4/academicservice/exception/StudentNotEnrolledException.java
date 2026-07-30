package org.p4.academicservice.exception;

import java.util.UUID;

public class StudentNotEnrolledException extends RuntimeException {
    public StudentNotEnrolledException(UUID id) {
        super("Student with id '" + id + "' is not enrolled!");
    }
}
