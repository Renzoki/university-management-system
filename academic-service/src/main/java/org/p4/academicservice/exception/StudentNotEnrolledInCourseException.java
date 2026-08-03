package org.p4.academicservice.exception;

import java.util.UUID;

public class StudentNotEnrolledInCourseException extends RuntimeException {
    public StudentNotEnrolledInCourseException(UUID studentId, String courseCode) {
            super("Student with id '" + studentId + "' is not enrolled in course with code '" + courseCode + "'!");
    }
}
