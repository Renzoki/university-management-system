package org.p4.academicservice.exception;

import java.util.UUID;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(UUID id) {
        super("Student with id '" + id + "' does not exist!");
    }
}
