package org.p4.academicservice.service;

import org.p4.academicservice.model.dto.request.NewCourseRequest;
import org.p4.academicservice.model.dto.request.UpdateCourseRequest;
import org.p4.academicservice.model.entity.Course;
import org.p4.academicservice.model.entity.Student;

import java.util.List;
import java.util.UUID;

public interface CourseService {
    Course getCourseById(UUID id);
    Course getCourseByCode(String code);
    List<Course> getAllCourses();
    List<Student> getAllStudentsByCourseId(UUID courseId);
    List<Student> getAllStudentsByCourseCode(String courseCode);
    Course addCourse(NewCourseRequest request);
    Course updateCourse(UUID id, UpdateCourseRequest request);
    void deleteCourseById(UUID id);
}
