package org.p4.academicservice.exception;

import java.util.UUID;

public class FacultyNotAssignedToCourseException extends RuntimeException {
    public FacultyNotAssignedToCourseException(UUID facultyId, String courseCode) {
        super("Student with id '" + facultyId + "' is not assigned to course with code '" + courseCode + "'!");
    }
}
