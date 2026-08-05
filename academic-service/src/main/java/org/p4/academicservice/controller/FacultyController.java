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

    /**
     * Retrieves the profile of the authenticated faculty member.
     *
     * @param authFaculty the authenticated faculty member
     * @return a {@code ResponseEntity} containing the faculty member's profile as a {@link FacultyDTO}
     */
    @GetMapping("/self")
    public ResponseEntity<FacultyDTO> getCurrentFaculty(
            @AuthenticationPrincipal AuthenticatedUser authFaculty
    ){
        Faculty faculty = facultyService.getFacultyById(authFaculty.id());
        FacultyDTO response = mapper.toDto(faculty);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a faculty member by their unique identifier.
     *
     * @param facultyId the unique identifier of the faculty member
     * @return a {@code ResponseEntity} containing the requested faculty member as a {@link FacultyDTO}
     */
    @GetMapping("/{facultyId}")
    public ResponseEntity<FacultyDTO> getFacultyById(
            @PathVariable UUID facultyId
    ){
        Faculty faculty = facultyService.getFacultyById(facultyId);
        FacultyDTO response = mapper.toDto(faculty);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all faculty members.
     *
     * @return a {@code ResponseEntity} containing a list of all faculty members as {@link FacultyDTO} objects
     */
    @GetMapping
    public ResponseEntity<List<FacultyDTO>> getAllFacultyMembers(){
        List<Faculty> facultyList = facultyService.getAllFacultyMembers();
        return ResponseEntity.ok(
                facultyList.stream()
                        .map(mapper::toDto)
                        .toList()
        );
    }

    /**
     * Creates a new faculty member.
     *
     * @param request the details of the faculty member to be created
     * @return a {@code ResponseEntity} containing the newly created faculty member as a {@link FacultyDTO}
     */
    @PostMapping
    public ResponseEntity<FacultyDTO> addFaculty(
            @Valid @RequestBody NewFacultyRequest request
    ){
        Faculty faculty = facultyService.addFaculty(request);
        FacultyDTO response = mapper.toDto(faculty);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Updates an existing faculty member.
     *
     * @param facultyId the unique identifier of the faculty member to update
     * @param request the updated faculty member details
     * @return a {@code ResponseEntity} containing the updated faculty member as a {@link FacultyDTO}
     */
    @PutMapping("/{facultyId}")
    public ResponseEntity<FacultyDTO> updateFaculty(
            @PathVariable UUID facultyId,
            @Valid @RequestBody UpdateFacultyRequest request
    ){
        Faculty faculty = facultyService.updateFaculty(facultyId, request);
        FacultyDTO response = mapper.toDto(faculty);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a faculty member by their unique identifier.
     *
     * @param facultyId the unique identifier of the faculty member to delete
     * @return a {@code ResponseEntity} with no content upon successful deletion
     */
    @DeleteMapping("/{facultyId}")
    public ResponseEntity<Void> deleteFaculty(
            @PathVariable UUID facultyId
    ){
        facultyService.deleteFaculty(facultyId);
        return ResponseEntity.noContent().build();
    }
}
