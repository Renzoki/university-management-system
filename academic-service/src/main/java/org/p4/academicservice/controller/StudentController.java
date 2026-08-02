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

    @GetMapping("/self")
    public ResponseEntity<StudentDTO> getCurrentStudent(
            @AuthenticationPrincipal AuthenticatedUser authStudent){
        Student student = studentService.getStudentById(authStudent.id());
        return ResponseEntity.ok(mapper.toDto(student));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(
            @PathVariable UUID id
    ){
        Student student = studentService.getStudentById(id);
        return ResponseEntity.ok(mapper.toDto(student));
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents(){
        return ResponseEntity.ok(
                studentService.getAllStudents().stream()
                        .map(mapper::toDto)
                        .toList()
        );
    }

    @PostMapping
    public ResponseEntity<StudentDTO> addStudent(
            @Valid @RequestBody NewStudentRequest request
    ){
        Student student = studentService.addStudent(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.toDto(student));
    }

    @PutMapping("{id}")
    public ResponseEntity<StudentDTO> updateStudent(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateStudentRequest request
    ){
        Student student = studentService.updateStudent(id, request);
        return ResponseEntity.ok(mapper.toDto(student));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable UUID id
    ){
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
