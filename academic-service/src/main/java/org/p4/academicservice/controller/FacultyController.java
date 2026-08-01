package org.p4.academicservice.controller;

import jakarta.validation.Valid;
import org.p4.academicservice.mapper.ResponseMapper;
import org.p4.academicservice.model.dto.request.NewFacultyRequest;
import org.p4.academicservice.model.dto.request.UpdateFacultyRequest;
import org.p4.academicservice.model.dto.response.FacultyDTO;
import org.p4.academicservice.model.entity.Faculty;
import org.p4.academicservice.service.FacultyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("faculties")
public class FacultyController {
    private final FacultyService facultyService;
    private final ResponseMapper mapper;

    public FacultyController(FacultyService facultyService, ResponseMapper mapper){
        this.facultyService = facultyService;
        this.mapper = mapper;
    }

    @GetMapping("{id}")
    public ResponseEntity<FacultyDTO> getFacultyById(@PathVariable UUID id){
        Faculty faculty = facultyService.getFacultyById(id);
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
    public ResponseEntity<FacultyDTO> addFaculty(@Valid @RequestBody NewFacultyRequest request){
        Faculty faculty = facultyService.addFaculty(request);
        FacultyDTO response = mapper.toDto(faculty);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("{id}")
    public ResponseEntity<FacultyDTO> updateFaculty(@PathVariable UUID id,
                                                    @Valid @RequestBody UpdateFacultyRequest request){
        Faculty faculty = facultyService.updateFaculty(id, request);
        FacultyDTO response = mapper.toDto(faculty);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable UUID id){
        facultyService.deleteFaculty(id);
        return ResponseEntity.noContent().build();
    }
}
