package org.p4.academicservice.service;

import org.p4.academicservice.model.entity.Enrollment;

import java.util.List;
import java.util.UUID;

public interface EnrollmentService {
    List<Enrollment> getStudentEnrollments(UUID studentId);
    List<Enrollment> getCourseEnrollments(UUID courseId);
    Enrollment addNewEnrollment(UUID studentId, UUID courseId);
    void dropEnrollment(UUID enrollmentId);
    void deleteEnrollment(UUID enrollmentId);
}
