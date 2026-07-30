package org.p4.academicservice.service.impl;

import org.p4.academicservice.exception.*;
import org.p4.academicservice.model.entity.Course;
import org.p4.academicservice.model.entity.Enrollment;
import org.p4.academicservice.model.entity.Student;
import org.p4.academicservice.model.entity.enums.CourseStatus;
import org.p4.academicservice.model.entity.enums.EnrollmentStatus;
import org.p4.academicservice.model.entity.enums.StudentStatus;
import org.p4.academicservice.repository.CourseRepository;
import org.p4.academicservice.repository.EnrollmentRepository;
import org.p4.academicservice.repository.StudentRepository;
import org.p4.academicservice.service.EnrollmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
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
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        return student.getEnrollmentList();
    }

    @Override
    public List<Enrollment> getCourseEnrollments(UUID courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        return course.getEnrollmentList();
    }

    @Override
    public Enrollment addNewEnrollment(UUID studentId, UUID courseId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException(studentId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        if(course.getStatus() != CourseStatus.OFFERED){
            throw new CourseNotOfferedException(courseId);
        }

        Enrollment enrollment = new Enrollment(student, course);

        if(student.getStatus() != StudentStatus.ENROLLED){
            student.setStatus(StudentStatus.ENROLLED);
        }

        student.addEnrollment(enrollment);
        course.addEnrollment(enrollment);
        studentRepository.save(student);
        return enrollmentRepository.save(enrollment);
    }

    @Override
    public void dropEnrollment(UUID enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new EnrollmentNotFoundException(enrollmentId));

        if(enrollment.getStatus() == EnrollmentStatus.COMPLETED){
            throw new EnrollmentAlreadyCompletedException(enrollmentId);
        } else {
            enrollment.setStatus(EnrollmentStatus.DROPPED);
            enrollmentRepository.save(enrollment);
        }
    }

    @Override
    public void deleteEnrollment(UUID enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new EnrollmentNotFoundException(enrollmentId));

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
