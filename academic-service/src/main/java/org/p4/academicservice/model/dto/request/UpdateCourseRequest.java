package org.p4.academicservice.model.dto.request;

import jakarta.validation.constraints.Size;
import org.p4.academicservice.annotation.NullOrNotBlank;
import org.p4.academicservice.model.entity.enums.CourseStatus;

import java.util.UUID;

public record UpdateCourseRequest(
        @NullOrNotBlank(message = COURSE_NAME_NULL_OR_NOT_BLANK)
        @Size(min = 10, max = 100, message = COURSE_NAME_LENGTH_ERROR)
        String courseName,

        @NullOrNotBlank(message = COURSE_CODE_NULL_OR_NOT_BLANK)
        @Size(min = 7, max = 7, message = COURSE_CODE_LENGTH_ERROR)
        String courseCode,

        CourseStatus status
) {
    private static final String COURSE_NAME_NULL_OR_NOT_BLANK = "Course name must be null or not blank!";
    private static final String COURSE_NAME_LENGTH_ERROR = "Course name must be between 10 and 100 characters!";
    private static final String COURSE_CODE_NULL_OR_NOT_BLANK = "Course code must be null or not blank!";
    private static final String COURSE_CODE_LENGTH_ERROR = "Course code must be exactly 7 characters!";
}
