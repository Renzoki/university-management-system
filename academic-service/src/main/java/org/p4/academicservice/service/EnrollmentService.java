package org.p4.academicservice.service;

import org.p4.academicservice.model.entity.Enrollment;
import org.p4.academicservice.model.entity.enums.UserRole;

import java.util.List;
import java.util.UUID;

public interface EnrollmentService {
    /**
     * Retrieves all enrollments of the specified student.
     *
     * @param studentId the unique identifier of the student
     * @return a list of the student's enrollments
     */
    List<Enrollment> getStudentEnrollments(UUID studentId);

    /**
     * Retrieves all enrollments for the specified course.
     *
     * @param employeeId the unique identifier of the authenticated user
     * @param courseId the unique identifier of the course
     * @param role the role of the authenticated user
     * @return a list of enrollments for the course
     */
    List<Enrollment> getCourseEnrollments(UUID employeeId, UUID courseId, UserRole role);

    /**
     * Enrolls the specified student in the specified course.
     *
     * @param studentId the unique identifier of the student
     * @param courseId the unique identifier of the course
     * @return the newly created enrollment
     */
    Enrollment addNewEnrollment(UUID studentId, UUID courseId);

    /**
     * Drops the specified enrollment for the specified student.
     *
     * @param studentId the unique identifier of the student
     * @param enrollmentId the unique identifier of the enrollment to drop
     */
    void dropEnrollment(UUID studentId, UUID enrollmentId);

    /**
     * Completes the specified enrollment for the specified student.
     *
     * @param studentId the unique identifier of the student
     * @param enrollmentId the unique identifier of the enrollment to drop
     */
    void completeEnrollment(UUID studentId, UUID enrollmentId);

    /**
     * Permanently deletes an enrollment.
     *
     * @param enrollmentId the unique identifier of the enrollment to delete
     */
    void deleteEnrollment(UUID enrollmentId);
}
