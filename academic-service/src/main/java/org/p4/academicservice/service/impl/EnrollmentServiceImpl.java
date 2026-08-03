package org.p4.academicservice.service.impl;

import org.p4.academicservice.exception.*;
import org.p4.academicservice.model.entity.Course;
import org.p4.academicservice.model.entity.Enrollment;
import org.p4.academicservice.model.entity.Student;
import org.p4.academicservice.model.entity.enums.CourseStatus;
import org.p4.academicservice.model.entity.enums.EnrollmentStatus;
import org.p4.academicservice.model.entity.enums.StudentStatus;
import org.p4.academicservice.model.entity.enums.UserRole;
import org.p4.academicservice.repository.CourseRepository;
import org.p4.academicservice.repository.EnrollmentRepository;
import org.p4.academicservice.repository.StudentRepository;
import org.p4.academicservice.service.EnrollmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
                                 CourseRepository courseRepository,
                                 StudentRepository studentRepository){
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Enrollment> getStudentEnrollments(UUID studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        return student.getEnrollmentList();
    }

    @Override
    public List<Enrollment> getCourseEnrollments(UUID employeeId, UUID courseId, UserRole role) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        if(role == UserRole.FACULTY && !employeeId.equals(course.getFaculty().getId())){
            throw new FacultyNotAssignedToCourseException(employeeId, course.getCourseCode());
        }
        return course.getEnrollmentList();
    }

    @Override
    public Enrollment addNewEnrollment(UUID studentId, UUID courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        if(course.getStatus() != CourseStatus.OFFERED){
            throw new CourseNotOfferedException(courseId);
        }

        if(enrollmentRepository.existsByStudentIdAndCourseIdAndStatus(studentId, courseId, EnrollmentStatus.ACTIVE)){
            throw new StudentAlreadyEnrolledInCourseException(studentId, course.getCourseCode());
        }

        if(enrollmentRepository.existsByStudentIdAndCourseIdAndStatus(studentId, courseId, EnrollmentStatus.COMPLETED)){
            throw new EnrollmentAlreadyCompletedException(studentId, courseId);
        }

        Enrollment enrollment = new Enrollment(student, course);

        if(student.getStatus() != StudentStatus.ENROLLED){
            student.setStatus(StudentStatus.ENROLLED);
        }

        student.addEnrollment(enrollment);
        course.addEnrollment(enrollment);
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public void dropEnrollment(UUID studentId, UUID enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findByIdAndStudentId(enrollmentId, studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment", "id", enrollmentId));

        if(enrollment.getStatus() == EnrollmentStatus.COMPLETED){
            throw new EnrollmentAlreadyCompletedException(enrollmentId);
        }

        if(enrollment.getStatus() == EnrollmentStatus.DROPPED){
            throw new EnrollmentAlreadyDroppedException(enrollmentId);
        }

        enrollment.setStatus(EnrollmentStatus.DROPPED);
        enrollmentRepository.save(enrollment);
    }

    @Override
    public void deleteEnrollment(UUID enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new  ResourceNotFoundException("Enrollment", "id", enrollmentId));

        if(enrollment.getStatus() == EnrollmentStatus.COMPLETED){
            throw new EnrollmentAlreadyCompletedException(enrollmentId);
        }

        if(enrollment.getStatus() == EnrollmentStatus.ACTIVE) {
            throw new EnrollmentStillActiveException(enrollmentId);
        }

        if(enrollment.getStatus() == EnrollmentStatus.DROPPED) {
            enrollmentRepository.delete(enrollment);
        }
    }
}
