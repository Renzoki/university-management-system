package org.p4.academicservice.service.impl;

import org.p4.academicservice.exception.*;
import org.p4.academicservice.model.dto.request.NewCourseRequest;
import org.p4.academicservice.model.dto.request.UpdateCourseRequest;
import org.p4.academicservice.model.entity.Course;
import org.p4.academicservice.model.entity.Enrollment;
import org.p4.academicservice.model.entity.Faculty;
import org.p4.academicservice.model.entity.Student;
import org.p4.academicservice.model.entity.enums.CourseStatus;
import org.p4.academicservice.model.entity.enums.EnrollmentStatus;
import org.p4.academicservice.model.entity.enums.UserRole;
import org.p4.academicservice.repository.CourseRepository;
import org.p4.academicservice.repository.EnrollmentRepository;
import org.p4.academicservice.repository.FacultyRepository;
import org.p4.academicservice.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final FacultyRepository facultyRepository;

    public CourseServiceImpl(CourseRepository courseRepository,
                             EnrollmentRepository enrollmentRepository,
                             FacultyRepository facultyRespository
    ){
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.facultyRepository = facultyRespository;
    }

    @Override
    public Course getCourseById(UUID id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));
    }

    @Override
    public Course getCourseByCode(String code) {
        return courseRepository.findByCourseCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "course code", code));
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .filter(course -> course.getStatus() == CourseStatus.OFFERED)
                .toList();
    }

    @Override
    public List<Student> getAllStudentsByCourseId(UUID employeeId, UserRole role, UUID courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        if(role == UserRole.FACULTY){
            if(course.getFaculty() == null){
                throw new ResourceNotFoundException("Faculty", "id", employeeId);
            }
            if(!course.getFaculty().getId().equals(employeeId)) {
                throw new FacultyNotAssignedToCourseException(employeeId, course.getCourseCode());
            }
        }

        return enrollmentRepository.findByCourseId(courseId)
                .stream()
                .map(Enrollment::getStudent)
                .toList();
    }

    @Override
    public List<Student> getAllStudentsByCourseCode(UUID employeeId, UserRole role, String courseCode) {
        Course course = courseRepository.findByCourseCode(courseCode)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "course code", courseCode));

        if(role == UserRole.FACULTY){
            if(course.getFaculty() == null){
                throw new CourseHasNoFacultyAssignedException(course.getId());
            }
            if(!course.getFaculty().getId().equals(employeeId)) {
                throw new FacultyNotAssignedToCourseException(employeeId, course.getCourseCode());
            }
        }

        return enrollmentRepository.findByCourse_CourseCode(courseCode)
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
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));

        if(request.courseName() != null){
            updatedCourse.setCourseName(request.courseName());
        }

        if (request.courseCode() != null) {
            if (!request.courseCode().equals(updatedCourse.getCourseCode())
                    && courseRepository.existsByCourseCode(request.courseCode())) {
                throw new CourseAlreadyExistsException(request.courseCode());
            }

            updatedCourse.setCourseCode(request.courseCode());
        }

        if(request.status() != null){
            updatedCourse.setStatus(request.status());
        }

        return courseRepository.save(updatedCourse);
    }

    @Override
    public Course assignFacultyToCourse(UUID courseId, UUID facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new ResourceNotFoundException("Faculty", "id", facultyId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        course.assignFaculty(faculty);
        return courseRepository.save(course);
    }


    @Override
    public void deleteCourseById(UUID id) {
        Course course = courseRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));

        if(course.getStatus() != CourseStatus.ARCHIVED){
            throw new CourseDeletionNotAllowedException(course.getCourseCode(), course.getStatus());
        }

        if(enrollmentRepository.existsByCourseIdAndStatus(id, EnrollmentStatus.ACTIVE)){
            throw new CourseDeletionNotAllowedException(course.getCourseCode());
        }

        courseRepository.delete(course);
    }

}
