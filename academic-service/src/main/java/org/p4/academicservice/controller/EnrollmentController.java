package org.p4.academicservice.controller;

import org.p4.academicservice.configuration.security.AuthenticatedUser;
import org.p4.academicservice.mapper.ResponseMapper;
import org.p4.academicservice.model.dto.response.EnrollmentDTO;
import org.p4.academicservice.model.entity.Enrollment;
import org.p4.academicservice.service.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    private final ResponseMapper mapper;

    public EnrollmentController(EnrollmentService enrollmentService, ResponseMapper mapper){
        this.enrollmentService = enrollmentService;
        this.mapper = mapper;
    }

    @GetMapping("/student/self")
    public ResponseEntity<List<EnrollmentDTO>> getCurrentStudentEnrollments(
            @AuthenticationPrincipal AuthenticatedUser student
            ){
        List<Enrollment> enrollments = enrollmentService.getStudentEnrollments(student.id());
        List<EnrollmentDTO> response = enrollments
                .stream()
                .map(mapper::toDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity<List<EnrollmentDTO>> getStudentEnrollments(
            @PathVariable UUID studentId
    ){
        List<Enrollment> enrollments = enrollmentService.getStudentEnrollments(studentId);
        List<EnrollmentDTO> response = enrollments
                .stream()
                .map(mapper::toDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<List<EnrollmentDTO>> getCourseEnrollments(
            @AuthenticationPrincipal AuthenticatedUser employee,
            @PathVariable UUID courseId
    ){
        List<Enrollment> enrollments = enrollmentService.getCourseEnrollments(employee.id(), courseId, employee.role());
        List<EnrollmentDTO> response = enrollments
                .stream()
                .map(mapper::toDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/self/{courseId}")
    public ResponseEntity<EnrollmentDTO> addCurrentStudentEnrollment(
            @AuthenticationPrincipal AuthenticatedUser authStudent,
            @PathVariable UUID courseId
    ){
        Enrollment enrollment = enrollmentService.addNewEnrollment(authStudent.id(), courseId);
        EnrollmentDTO response = mapper.toDto(enrollment);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/{studentId}/{courseId}")
    public ResponseEntity<EnrollmentDTO> addEnrollment(
            @PathVariable UUID studentId,
            @PathVariable UUID courseId
    ){
        Enrollment enrollment = enrollmentService.addNewEnrollment(studentId, courseId);
        EnrollmentDTO response = mapper.toDto(enrollment);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/self/{enrollmentId}")
    public ResponseEntity<Void> dropCurrentStudentEnrollment(
            @AuthenticationPrincipal AuthenticatedUser authStudent,
            @PathVariable UUID enrollmentId
    ){
        enrollmentService.dropEnrollment(authStudent.id(), enrollmentId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{studentId}/{enrollmentId}")
    public ResponseEntity<Void> dropEnrollment(
            @PathVariable UUID studentId,
            @PathVariable UUID enrollmentId
    ){
        enrollmentService.dropEnrollment(studentId, enrollmentId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{enrollmentId}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable UUID enrollmentId){
        enrollmentService.deleteEnrollment(enrollmentId);
        return ResponseEntity.noContent().build();
    }

}
