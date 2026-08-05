package org.p4.academicservice.service;

import org.p4.academicservice.model.dto.request.NewCourseRequest;
import org.p4.academicservice.model.dto.request.UpdateCourseRequest;
import org.p4.academicservice.model.entity.Course;
import org.p4.academicservice.model.entity.Student;
import org.p4.academicservice.model.entity.enums.UserRole;

import java.util.List;
import java.util.UUID;

public interface CourseService {
    /**
     * Retrieves a course by its unique identifier.
     *
     * @param id the unique identifier of the course
     * @return the requested course
     */
    Course getCourseById(UUID id);

    /**
     * Retrieves a course by its course code.
     *
     * @param code the unique course code of the course
     * @return the requested course
     */
    Course getCourseByCode(String code);

    /**
     * Retrieves all courses.
     *
     * @return a list of all courses
     */
    List<Course> getAllCourses();

    /**
     * Retrieves all students enrolled in the specified course.
     *
     * @param employeeId the unique identifier of the authenticated user
     * @param role the role of the authenticated user
     * @param courseId the unique identifier of the course
     * @return a list of students enrolled in the course
     */
    List<Student> getAllStudentsByCourseId(UUID employeeId, UserRole role, UUID courseId);

    /**
     * Retrieves all students enrolled in the course with the specified course code.
     *
     * @param employeeId the unique identifier of the authenticated user
     * @param role the role of the authenticated user
     * @param courseCode the unique course code of the course
     * @return a list of students enrolled in the course
     */
    List<Student> getAllStudentsByCourseCode(UUID employeeId, UserRole role, String courseCode);

    /**
     * Creates a new course.
     *
     * @param request the details of the course to be created
     * @return the newly created course
     */
    Course addCourse(NewCourseRequest request);

    /**
     * Updates an existing course.
     *
     * @param id the unique identifier of the course to update
     * @param request the updated course details
     * @return the updated course
     */
    Course updateCourse(UUID id, UpdateCourseRequest request);

    /**
     * Assigns a faculty member to a course.
     *
     * @param courseId the unique identifier of the course
     * @param facultyId the unique identifier of the faculty member
     * @return the updated course
     */
    Course assignFacultyToCourse(UUID courseId, UUID facultyId);

    /**
     * Deletes a course by its unique identifier.
     *
     * @param id the unique identifier of the course to delete
     */
    void deleteCourseById(UUID id);
}
