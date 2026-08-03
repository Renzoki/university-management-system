package org.p4.academicservice.service;

import org.p4.academicservice.model.entity.Enrollment;
import org.p4.academicservice.model.entity.enums.UserRole;

import java.util.List;
import java.util.UUID;

public interface EnrollmentService {
    List<Enrollment> getStudentEnrollments(UUID studentId);
    List<Enrollment> getCourseEnrollments(UUID employeeId, UUID courseId, UserRole role);
    Enrollment addNewEnrollment(UUID studentId, UUID courseId);
    void dropEnrollment(UUID studentId, UUID enrollmentId);
    void deleteEnrollment(UUID enrollmentId);
}
