package org.p4.academicservice.controller;

import jakarta.validation.Valid;
import org.p4.academicservice.configuration.security.AuthenticatedUser;
import org.p4.academicservice.mapper.ResponseMapper;
import org.p4.academicservice.model.dto.request.NewFacultyRequest;
import org.p4.academicservice.model.dto.request.UpdateFacultyRequest;
import org.p4.academicservice.model.dto.response.FacultyDTO;
import org.p4.academicservice.model.entity.Faculty;
import org.p4.academicservice.service.FacultyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;
    private final ResponseMapper mapper;

    public FacultyController(FacultyService facultyService, ResponseMapper mapper){
        this.facultyService = facultyService;
        this.mapper = mapper;
    }

    @GetMapping("/self")
    public ResponseEntity<FacultyDTO> getCurrentFaculty(
            @AuthenticationPrincipal AuthenticatedUser authFaculty
    ){
        Faculty faculty = facultyService.getFacultyById(authFaculty.id());
        FacultyDTO response = mapper.toDto(faculty);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{facultyId}")
    public ResponseEntity<FacultyDTO> getFacultyById(
            @PathVariable UUID facultyId
    ){
        Faculty faculty = facultyService.getFacultyById(facultyId);
        FacultyDTO response = mapper.toDto(faculty);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<FacultyDTO>> getAllFacultyMembers(){
        List<Faculty> facultyList = facultyService.getAllFacultyMembers();
        return ResponseEntity.ok(
                facultyList.stream()
                        .map(mapper::toDto)
                        .toList()
        );
    }

    @PostMapping
    public ResponseEntity<FacultyDTO> addFaculty(
            @Valid @RequestBody NewFacultyRequest request
    ){
        Faculty faculty = facultyService.addFaculty(request);
        FacultyDTO response = mapper.toDto(faculty);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{facultyId}")
    public ResponseEntity<FacultyDTO> updateFaculty(
            @PathVariable UUID facultyId,
            @Valid @RequestBody UpdateFacultyRequest request
    ){
        Faculty faculty = facultyService.updateFaculty(facultyId, request);
        FacultyDTO response = mapper.toDto(faculty);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{facultyId}")
    public ResponseEntity<Void> deleteFaculty(
            @PathVariable UUID facultyId
    ){
        facultyService.deleteFaculty(facultyId);
        return ResponseEntity.noContent().build();
    }
}
