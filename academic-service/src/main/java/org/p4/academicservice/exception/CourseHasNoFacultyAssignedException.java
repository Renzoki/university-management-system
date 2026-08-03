package org.p4.academicservice.exception;

import java.util.UUID;

public class CourseHasNoFacultyAssignedException extends RuntimeException {
    public CourseHasNoFacultyAssignedException(UUID courseId) {
        super("Course with id '" + courseId + "' does not have an assigned faculty yet!");
    }

    public CourseHasNoFacultyAssignedException(String courseCode) {
        super("Course with course code '" + courseCode + "' does not have an assigned faculty yet!");
    }
}
