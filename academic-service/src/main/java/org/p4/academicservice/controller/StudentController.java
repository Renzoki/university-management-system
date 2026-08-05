package org.p4.academicservice.controller;

import jakarta.validation.Valid;
import org.p4.academicservice.configuration.security.AuthenticatedUser;
import org.p4.academicservice.mapper.ResponseMapper;
import org.p4.academicservice.model.dto.request.NewStudentRequest;
import org.p4.academicservice.model.dto.request.UpdateStudentRequest;
import org.p4.academicservice.model.dto.response.StudentDTO;
import org.p4.academicservice.model.entity.Student;
import org.p4.academicservice.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private final ResponseMapper mapper;

    public StudentController(StudentService studentService, ResponseMapper mapper){
        this.studentService = studentService;
        this.mapper = mapper;
    }

    /**
     * Retrieves the profile of the authenticated student.
     *
     * @param authStudent the authenticated student
     * @return a {@code ResponseEntity} containing the student's profile as a {@link StudentDTO}
     */
    @GetMapping("/self")
    public ResponseEntity<StudentDTO> getCurrentStudent(
            @AuthenticationPrincipal AuthenticatedUser authStudent){
        Student student = studentService.getStudentById(authStudent.id());
        return ResponseEntity.ok(mapper.toDto(student));
    }

    /**
     * Retrieves a student by their unique identifier.
     *
     * @param studentId the unique identifier of the student
     * @return a {@code ResponseEntity} containing the requested student as a {@link StudentDTO}
     */
    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDTO> getStudentById(
            @PathVariable UUID studentId
    ){
        Student student = studentService.getStudentById(studentId);
        return ResponseEntity.ok(mapper.toDto(student));
    }

    /**
     * Retrieves all students.
     *
     * @return a {@code ResponseEntity} containing a list of all students as {@link StudentDTO} objects
     */
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents(){
        return ResponseEntity.ok(
                studentService.getAllStudents().stream()
                        .map(mapper::toDto)
                        .toList()
        );
    }

    /**
     * Creates a new student.
     *
     * @param request the details of the student to be created
     * @return a {@code ResponseEntity} containing the newly created student as a {@link StudentDTO}
     */
    @PostMapping
    public ResponseEntity<StudentDTO> addStudent(
            @Valid @RequestBody NewStudentRequest request
    ){
        Student student = studentService.addStudent(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.toDto(student));
    }

    /**
     * Updates an existing student.
     *
     * @param studentId the unique identifier of the student to update
     * @param request the updated student details
     * @return a {@code ResponseEntity} containing the updated student as a {@link StudentDTO}
     */
    @PutMapping("/{studentId}")
    public ResponseEntity<StudentDTO> updateStudent(
            @PathVariable UUID studentId,
            @Valid @RequestBody UpdateStudentRequest request
    ){
        Student student = studentService.updateStudent(studentId, request);
        return ResponseEntity.ok(mapper.toDto(student));
    }

    /**
     * Deletes a student by their unique identifier.
     *
     * @param studentId the unique identifier of the student to delete
     * @return a {@code ResponseEntity} with no content upon successful deletion
     */
    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable UUID studentId
    ){
        studentService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }
}
