package org.p4.academicservice.mapper;

import org.p4.academicservice.model.dto.response.CourseDTO;
import org.p4.academicservice.model.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
    public CourseDTO toDto(Course course){
        return new CourseDTO(course.getCourseName(), course.getCourseCode());
    }
}
