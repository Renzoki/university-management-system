package org.p4.academicservice.service.impl;

import org.p4.academicservice.exception.*;
import org.p4.academicservice.model.dto.request.SetGradeRequest;
import org.p4.academicservice.model.entity.Course;
import org.p4.academicservice.model.entity.Enrollment;
import org.p4.academicservice.model.entity.Grade;
import org.p4.academicservice.model.entity.enums.CourseStatus;
import org.p4.academicservice.model.entity.enums.EnrollmentStatus;
import org.p4.academicservice.model.entity.enums.StudentStatus;
import org.p4.academicservice.model.entity.enums.UserRole;
import org.p4.academicservice.repository.CourseRepository;
import org.p4.academicservice.repository.EnrollmentRepository;
import org.p4.academicservice.repository.GradeRepository;
import org.p4.academicservice.service.GradeService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GradeServiceImpl implements GradeService {
    private final CourseRepository courseRepository;
    private final GradeRepository gradeRepository;
    private final EnrollmentRepository enrollmentRepository;

    public GradeServiceImpl(GradeRepository gradeRepository,
                            EnrollmentRepository enrollmentRepository,
                            CourseRepository courseRepository
    ){
        this.gradeRepository = gradeRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Grade getGrade(UUID studentId, UUID courseId) {
        Enrollment enrollment = fetchAndValidateEnrollment(studentId, courseId);
        return enrollment.getGrade();
    }

    @Override
    public Grade getGrade(UUID employeeId, UUID studentId, UUID courseId, UserRole role) {
        Enrollment enrollment = fetchAndValidateEnrollment(studentId, courseId);
        validateFacultyAssignment(employeeId, enrollment, role);
        return enrollment.getGrade();
    }

    @Override
    public Grade setGrade(UUID employeeId, UUID enrollmentId, UserRole role, SetGradeRequest request) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new EnrollmentNotFoundException(enrollmentId));

        validateSystemStates(enrollment);
        validateFacultyAssignment(employeeId, enrollment, role);
        double gradeEquivalent = calculateGradeEquivalent(request.rawGrade());
        Grade grade = enrollment.getGrade();

        if(grade == null){
            grade = new Grade(request.rawGrade(), enrollment);
            grade.setGradeEquivalent(gradeEquivalent);
        } else {
            grade.setRawGrade(request.rawGrade());
            grade.setGradeEquivalent(gradeEquivalent);
        }

        return gradeRepository.save(grade);
    }

    private Enrollment fetchAndValidateEnrollment(UUID studentId, UUID courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new EnrollmentNotFoundException(studentId, course.getCourseCode()));

        if (enrollment.getGrade() == null) {
            throw new GradeNotFoundException(enrollment.getId());
        }

        return enrollment;
    }

    private void validateFacultyAssignment(UUID actorId, Enrollment enrollment, UserRole role){
        UUID facultyId = enrollment.getCourse().getFaculty().getId();

        if(role == UserRole.FACULTY){
            if(!actorId.equals(facultyId)){
                throw new FacultyNotAssignedToCourseException(actorId, enrollment.getCourse().getCourseCode());
            }
        }
    }

    private void validateSystemStates(Enrollment enrollment) {
        if (enrollment.getStatus() != EnrollmentStatus.ACTIVE) {
            throw new EnrollmentNotActiveException(enrollment.getId());
        }

        if (enrollment.getStudent().getStatus() != StudentStatus.ENROLLED) {
            throw new StudentNotEnrolledException(enrollment.getStudent().getId());
        }

        if (enrollment.getCourse().getStatus() != CourseStatus.OFFERED) {
            throw new CourseNotOfferedException(enrollment.getCourse().getId());
        }
    }

    private double calculateGradeEquivalent(double rawGrade) {
        if (rawGrade < 60) return 0.0;
        if (rawGrade < 65) return 1.0;
        if (rawGrade < 70) return 1.5;
        if (rawGrade < 75) return 2.0;
        if (rawGrade < 80) return 2.5;
        if (rawGrade < 85) return 3.0;
        if (rawGrade < 95) return 3.5;
        return 4.0;
    }
}

