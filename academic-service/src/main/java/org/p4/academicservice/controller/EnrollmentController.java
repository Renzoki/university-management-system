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

    /**
     * Retrieves all enrollments of the authenticated student.
     *
     * @param student the authenticated student
     * @return a {@code ResponseEntity} containing a list of the student's enrollments as {@link EnrollmentDTO} objects
     */
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

    /**
     * Retrieves all enrollments of the specified student.
     *
     * @param studentId the unique identifier of the student
     * @return a {@code ResponseEntity} containing a list of the student's enrollments as {@link EnrollmentDTO} objects
     */
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

    /**
     * Retrieves all enrollments for the specified course.
     *
     * @param employee the authenticated user requesting the course enrollments
     * @param courseId the unique identifier of the course
     * @return a {@code ResponseEntity} containing a list of enrollments for the course as {@link EnrollmentDTO} objects
     */
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

    /**
     * Enrolls the authenticated student in the specified course.
     *
     * @param authStudent the authenticated student
     * @param courseId the unique identifier of the course
     * @return a {@code ResponseEntity} containing the newly created enrollment as a {@link EnrollmentDTO}
     */
    @PostMapping("/self/{courseId}")
    public ResponseEntity<EnrollmentDTO> addCurrentStudentEnrollment(
            @AuthenticationPrincipal AuthenticatedUser authStudent,
            @PathVariable UUID courseId
    ){
        Enrollment enrollment = enrollmentService.addNewEnrollment(authStudent.id(), courseId);
        EnrollmentDTO response = mapper.toDto(enrollment);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Enrolls the specified student in the specified course.
     *
     * @param studentId the unique identifier of the student
     * @param courseId the unique identifier of the course
     * @return a {@code ResponseEntity} containing the newly created enrollment as a {@link EnrollmentDTO}
     */
    @PostMapping("/{studentId}/{courseId}")
    public ResponseEntity<EnrollmentDTO> addEnrollment(
            @PathVariable UUID studentId,
            @PathVariable UUID courseId
    ){
        Enrollment enrollment = enrollmentService.addNewEnrollment(studentId, courseId);
        EnrollmentDTO response = mapper.toDto(enrollment);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Drops an enrollment for the authenticated student.
     *
     * @param authStudent the authenticated student
     * @param enrollmentId the unique identifier of the enrollment to drop
     * @return a {@code ResponseEntity} with no content upon successful completion
     */
    @PatchMapping("/self/{enrollmentId}")
    public ResponseEntity<Void> dropCurrentStudentEnrollment(
            @AuthenticationPrincipal AuthenticatedUser authStudent,
            @PathVariable UUID enrollmentId
    ){
        enrollmentService.dropEnrollment(authStudent.id(), enrollmentId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Drops the specified enrollment for the specified student.
     *
     * @param studentId the unique identifier of the student
     * @param enrollmentId the unique identifier of the enrollment to drop
     * @return a {@code ResponseEntity} with no content upon successful completion
     */
    @PatchMapping("/{studentId}/{enrollmentId}/drop")
    public ResponseEntity<Void> dropEnrollment(
            @PathVariable UUID studentId,
            @PathVariable UUID enrollmentId
    ){
        enrollmentService.dropEnrollment(studentId, enrollmentId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Completes the specified enrollment for the specified student.
     *
     * @param studentId the unique identifier of the student
     * @param enrollmentId the unique identifier of the enrollment to drop
     * @return a {@code ResponseEntity} with no content upon successful completion
     */
    @PatchMapping("/{studentId}/{enrollmentId}/complete")
    public ResponseEntity<Void> completeEnrollment(
            @PathVariable UUID studentId,
            @PathVariable UUID enrollmentId
    ){
        enrollmentService.completeEnrollment(studentId, enrollmentId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Permanently deletes an enrollment.
     *
     * @param enrollmentId the unique identifier of the enrollment to delete
     * @return a {@code ResponseEntity} with no content upon successful deletion
     */
    @DeleteMapping("/{enrollmentId}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable UUID enrollmentId){
        enrollmentService.deleteEnrollment(enrollmentId);
        return ResponseEntity.noContent().build();
    }

}
