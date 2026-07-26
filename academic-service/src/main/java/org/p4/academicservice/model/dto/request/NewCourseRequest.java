package org.p4.academicservice.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewCourseRequest(
        @NotBlank(message = COURSE_NAME_BLANK_ERROR)
        @Size(min = 10, max = 100, message = COURSE_NAME_LENGTH_ERROR)
        String courseName,

        @NotBlank(message = COURSE_CODE_BLANK_ERROR)
        @Size(min = 7, max = 7, message = COURSE_CODE_LENGTH_ERROR)
        String courseCode
) {
    private static final String COURSE_NAME_BLANK_ERROR = "Course name is required!";
    private static final String COURSE_NAME_LENGTH_ERROR = "Course name must be between 10 and 100 characters!";
    private static final String COURSE_CODE_BLANK_ERROR = "Course code is required!";
    private static final String COURSE_CODE_LENGTH_ERROR = "Course code must be exactly 7 characters!";
}
