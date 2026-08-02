package org.p4.academicservice.service.impl;

import org.p4.academicservice.exception.EnrollmentNotFoundException;
import org.p4.academicservice.exception.GradeNotFoundException;
import org.p4.academicservice.model.dto.request.SetGradeRequest;
import org.p4.academicservice.model.entity.Enrollment;
import org.p4.academicservice.model.entity.Grade;
import org.p4.academicservice.repository.EnrollmentRepository;
import org.p4.academicservice.repository.GradeRepository;
import org.p4.academicservice.service.GradeService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;
    private final EnrollmentRepository enrollmentRepository;

    public GradeServiceImpl(GradeRepository gradeRepository, EnrollmentRepository enrollmentRepository){
        this.gradeRepository = gradeRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public Grade getGrade(UUID enrollmentId) {
        return gradeRepository.findByEnrollmentId(enrollmentId)
                .orElseThrow(() -> new GradeNotFoundException(enrollmentId));
    }

    @Override
    public Grade setGrade(UUID enrollmentId, SetGradeRequest request) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new EnrollmentNotFoundException(enrollmentId));

        Grade grade = new Grade(request.rawGrade(), enrollment);
        return gradeRepository.save(grade);
    }
}

