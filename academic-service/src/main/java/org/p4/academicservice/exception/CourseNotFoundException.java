package org.p4.academicservice.exception;

import java.util.UUID;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(UUID id) {
        super("Course with id'" + id + "' does not exist!");
    }
    public CourseNotFoundException(String code) {
        super("Course with course code'" + code + "' does not exist!");
    }
}
