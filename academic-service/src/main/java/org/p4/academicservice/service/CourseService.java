package org.p4.academicservice.service;

import org.p4.academicservice.model.dto.request.NewCourseRequest;
import org.p4.academicservice.model.dto.request.UpdateCourseRequest;
import org.p4.academicservice.model.entity.Course;
import org.p4.academicservice.model.entity.Student;
import org.p4.academicservice.model.entity.enums.UserRole;

import java.util.List;
import java.util.UUID;

public interface CourseService {
    Course getCourseById(UUID id);
    Course getCourseByCode(String code);
    List<Course> getAllCourses();
    List<Student> getAllStudentsByCourseId(UUID employeeId, UserRole role, UUID courseId);
    List<Student> getAllStudentsByCourseCode(UUID employeeId, UserRole role, String courseCode);
    Course addCourse(NewCourseRequest request);
    Course updateCourse(UUID id, UpdateCourseRequest request);
    Course assignFacultyToCourse(UUID courseId, UUID facultyId);
    void deleteCourseById(UUID id);
}
