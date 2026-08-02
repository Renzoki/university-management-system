package org.p4.academicservice.controller;

import jakarta.validation.Valid;
import org.p4.academicservice.configuration.security.AuthenticatedUser;
import org.p4.academicservice.mapper.ResponseMapper;
import org.p4.academicservice.model.dto.request.SetGradeRequest;
import org.p4.academicservice.model.dto.response.GradeDTO;
import org.p4.academicservice.model.entity.Grade;
import org.p4.academicservice.service.GradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("grades")
public class GradeController {
    private final GradeService gradeService;
    private final ResponseMapper mapper;

    public GradeController(GradeService gradeService, ResponseMapper mapper){
        this.gradeService = gradeService;
        this.mapper = mapper;
    }

    @GetMapping("/self/{courseId}")
    public ResponseEntity<GradeDTO> getCurrentUserGrades(
            @AuthenticationPrincipal AuthenticatedUser student,
            @PathVariable UUID courseId
    ){
        Grade grade = gradeService.getGrade(student.id(), courseId);
        GradeDTO response = mapper.toDto(grade);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{studentId}/{courseId}")
    public ResponseEntity<GradeDTO> getGrade(
            @AuthenticationPrincipal AuthenticatedUser employee,
            @PathVariable UUID studentId,
            @PathVariable UUID courseId
    ){
        Grade grade = gradeService.getGrade(employee.id(), studentId, courseId, employee.role());
        GradeDTO response = mapper.toDto(grade);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{enrollmentId}")
    public ResponseEntity<GradeDTO> setGrade(
            @AuthenticationPrincipal AuthenticatedUser employee,
            @PathVariable UUID enrollmentId,
            @Valid @RequestBody SetGradeRequest request){
        Grade grade = gradeService.setGrade(employee.id(), enrollmentId, employee.role(), request);
        GradeDTO response = mapper.toDto(grade);

        return ResponseEntity.ok(response);
    }
}
