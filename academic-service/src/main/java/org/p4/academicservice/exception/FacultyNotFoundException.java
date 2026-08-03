package org.p4.academicservice.exception;

import java.util.UUID;

public class FacultyNotFoundException extends RuntimeException {
    public static FacultyNotFoundException facultyNotFound(UUID facultyId) {
        return new FacultyNotFoundException(
                "Faculty with id '" + facultyId + "' not found!");
    }

    public static FacultyNotFoundException courseHasNoFaculty(UUID courseId) {
        return new FacultyNotFoundException(
                "Course with id '" + courseId + "' does not have an assigned faculty yet!");
    }

    public FacultyNotFoundException(String courseCode) {
        super("Course with course code '" + courseCode + "' does not have an assigned faculty yet!");
    }
}
