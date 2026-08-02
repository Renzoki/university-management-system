package org.p4.academicservice.controller;

import jakarta.validation.Valid;
import org.p4.academicservice.mapper.ResponseMapper;
import org.p4.academicservice.model.dto.request.SetGradeRequest;
import org.p4.academicservice.model.dto.response.GradeDTO;
import org.p4.academicservice.model.entity.Grade;
import org.p4.academicservice.service.GradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class GradeController {
    private final GradeService gradeService;
    private final ResponseMapper mapper;

    public GradeController(GradeService gradeService, ResponseMapper mapper){
        this.gradeService = gradeService;
        this.mapper = mapper;
    }

    @GetMapping("{enrollmentId}")
    public ResponseEntity<GradeDTO> getGrade(@PathVariable UUID enrollmentId){
        Grade grade = gradeService.getGrade(enrollmentId);
        GradeDTO response = mapper.toDto(grade);

        return ResponseEntity.ok(response);
    }

    @PutMapping("{enrollmentId}")
    public ResponseEntity<GradeDTO> setGrade(@PathVariable UUID enrollmentId,
                                             @Valid @RequestBody SetGradeRequest request){
        Grade grade = gradeService.setGrade(enrollmentId, request);
        GradeDTO response = mapper.toDto(grade);

        return ResponseEntity.ok(response);
    }
}
