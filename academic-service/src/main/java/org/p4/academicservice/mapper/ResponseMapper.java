package org.p4.academicservice.mapper;

import org.p4.academicservice.model.dto.response.CourseDTO;
import org.p4.academicservice.model.dto.response.EnrollmentDTO;
import org.p4.academicservice.model.dto.response.GradeDTO;
import org.p4.academicservice.model.dto.response.StudentDTO;
import org.p4.academicservice.model.entity.Course;
import org.p4.academicservice.model.entity.Enrollment;
import org.p4.academicservice.model.entity.Grade;
import org.p4.academicservice.model.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class ResponseMapper {
    public CourseDTO toDto(Course course){
        return new CourseDTO(course.getId(),
                course.getCourseName(),
                course.getCourseCode(),
                course.getStatus());
    }

    public StudentDTO toDto(Student student){
        return new StudentDTO(student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getStatus()
        );
    }

    public GradeDTO toDto(Grade grade){
        if (grade == null) {
            return null;
        }

        return new GradeDTO(grade.getId(),
                grade.getRawGrade(),
                grade.getGradeEquivalent());
    }

    public EnrollmentDTO toDto(Enrollment enrollment){
        return new EnrollmentDTO(enrollment.getId(),
                this.toDto(enrollment.getCourse()),
                this.toDto(enrollment.getStudent()),
                this.toDto(enrollment.getGrade()),
                enrollment.getStatus()
                );
    }
}
