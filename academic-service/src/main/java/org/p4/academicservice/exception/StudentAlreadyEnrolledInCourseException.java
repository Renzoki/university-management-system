package org.p4.academicservice.exception;

import java.util.UUID;

public class StudentAlreadyEnrolledInCourseException extends RuntimeException {
    public StudentAlreadyEnrolledInCourseException(UUID studentId, String courseCode) {
        super("Student with id '" + studentId + "' is already enrolled in course with code '" + courseCode + "'!");
    }
}
