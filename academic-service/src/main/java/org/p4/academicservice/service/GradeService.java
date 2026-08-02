package org.p4.academicservice.service;

import org.p4.academicservice.configuration.security.AuthenticatedUser;
import org.p4.academicservice.model.dto.request.SetGradeRequest;
import org.p4.academicservice.model.entity.Grade;
import org.p4.academicservice.model.entity.enums.UserRole;

import java.util.UUID;

public interface GradeService {
    Grade getGrade(UUID studentId, UUID courseId);
    Grade getGrade(UUID employeeId, UUID studentId, UUID courseId, UserRole role);
    Grade setGrade(UUID employeeId, UUID enrollmentId, UserRole role, SetGradeRequest request);
}
