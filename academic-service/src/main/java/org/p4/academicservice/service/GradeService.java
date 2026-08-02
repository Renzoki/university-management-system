package org.p4.academicservice.service;

import org.p4.academicservice.model.dto.request.SetGradeRequest;
import org.p4.academicservice.model.entity.Grade;

import java.util.UUID;

public interface GradeService {
    Grade getGrade(UUID enrollmentId);
    Grade setGrade(UUID enrollmentId, SetGradeRequest request);
}
