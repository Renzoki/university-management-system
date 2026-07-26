package org.p4.academicservice.exception;

public class CourseAlreadyExistsException extends RuntimeException {
    public CourseAlreadyExistsException(String code) {
        super("Course with course code'" + code + "' already exists!");
    }
}
