package org.p4.academicservice.exception;

import java.util.UUID;

public class CourseNotOfferedException extends RuntimeException {
    public CourseNotOfferedException(UUID id) {
        super("Course with id '" + id + "' is not offered!");
    }
}
