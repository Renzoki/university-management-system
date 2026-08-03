package org.p4.academicservice.exception;

import java.util.UUID;

public class FacultyNotFoundException extends RuntimeException {
    public FacultyNotFoundException(UUID facultyId) {
        super("Faculty with id '" + facultyId + "' not found!");
    }
}
