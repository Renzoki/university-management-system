package org.p4.academicservice.service.impl;

import org.p4.academicservice.exception.CourseAlreadyExistsException;
import org.p4.academicservice.exception.CourseDeletionNotAllowedException;
import org.p4.academicservice.exception.CourseNotFoundException;
import org.p4.academicservice.model.dto.request.NewCourseRequest;
import org.p4.academicservice.model.dto.request.UpdateCourseRequest;
import org.p4.academicservice.model.entity.Course;
import org.p4.academicservice.model.entity.Enrollment;
import org.p4.academicservice.model.entity.Student;
import org.p4.academicservice.model.entity.enums.CourseStatus;
import org.p4.academicservice.model.entity.enums.EnrollmentStatus;
import org.p4.academicservice.repository.CourseRepository;
import org.p4.academicservice.repository.EnrollmentRepository;
import org.p4.academicservice.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public CourseServiceImpl(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository){
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public Course getCourseById(UUID id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
    }

    @Override
    public Course getCourseByCode(String code) {
        return courseRepository.findByCourseCode(code)
                .orElseThrow(() -> new CourseNotFoundException(code));
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .filter(course -> course.getStatus() == CourseStatus.OFFERED)
                .toList();
    }

    @Override
    public List<Student> getAllStudentsByCourseId(UUID courseId) {
        return enrollmentRepository.findByCourseId(courseId)
            .stream()
            .map(Enrollment::getStudent)
            .toList();
    }

    @Override
    public List<Student> getAllStudentsByCourseCode(String courseCode) {
        return enrollmentRepository.findByCourseCode(courseCode)
                .stream()
                .map(Enrollment::getStudent)
                .toList();
    }

    @Override
    public Course addCourse(NewCourseRequest request) {
        if(courseRepository.existsByCourseCode(request.courseCode())){
            throw new CourseAlreadyExistsException(request.courseCode());
        }

        Course course = new Course(request.courseName(), request.courseCode());
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(UUID id, UpdateCourseRequest request) {
        Course updatedCourse = courseRepository
                .findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));

        if(request.courseName() != null){
            updatedCourse.setCourseName(request.courseName());
        }

        if(request.courseCode() != null){
            updatedCourse.setCourseCode(request.courseCode());
        }

        if(request.status() != null){
            updatedCourse.setStatus(request.status());
        }

        return courseRepository.save(updatedCourse);
    }

    @Override
    public void deleteCourseById(UUID id) {
        Course course = courseRepository
                .findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));

        if(course.getStatus() != CourseStatus.ARCHIVED){
            throw new CourseDeletionNotAllowedException(course.getCourseCode(), course.getStatus());
        }

        if(enrollmentRepository.existsByCourseIdAndStatus(id, EnrollmentStatus.ACTIVE)){
            throw new CourseDeletionNotAllowedException(course.getCourseCode());
        }

        courseRepository.delete(course);
    }

}
