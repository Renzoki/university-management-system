package org.p4.academicservice.exception;

import org.p4.academicservice.model.entity.enums.CourseStatus;

public class CourseDeletionNotAllowedException extends RuntimeException {
    public CourseDeletionNotAllowedException(String courseCode) {
        super("Course with code '" + courseCode + "' still has active enrollees!");
    }

    public CourseDeletionNotAllowedException(String courseCode, CourseStatus status) {
        super("Course with code '" + courseCode + "' cannot be deleted with status '" + status +"'!");
    }
}
