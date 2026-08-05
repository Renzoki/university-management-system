package org.p4.academicservice.service;

import org.p4.academicservice.model.dto.request.SetGradeRequest;
import org.p4.academicservice.model.entity.Grade;
import org.p4.academicservice.model.entity.enums.UserRole;

import java.util.UUID;

public interface GradeService {
    /**
     * Retrieves the grade of the specified student for the specified course.
     *
     * @param studentId the unique identifier of the student
     * @param courseId the unique identifier of the course
     * @return the requested grade
     */
    Grade getGrade(UUID studentId, UUID courseId);

    /**
     * Retrieves the grade of the specified student for the specified course.
     *
     * @param employeeId the unique identifier of the authenticated user
     * @param studentId the unique identifier of the student
     * @param courseId the unique identifier of the course
     * @param role the role of the authenticated user
     * @return the requested grade
     */
    Grade getGrade(UUID employeeId, UUID studentId, UUID courseId, UserRole role);

    /**
     * Sets or updates the grade for the specified enrollment.
     *
     * @param employeeId the unique identifier of the authenticated user
     * @param enrollmentId the unique identifier of the enrollment
     * @param role the role of the authenticated user
     * @param request the grade details to be assigned
     * @return the updated grade
     */
    Grade setGrade(UUID employeeId, UUID enrollmentId, UserRole role, SetGradeRequest request);
}
