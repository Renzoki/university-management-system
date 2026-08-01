package org.p4.academicservice.service;

import org.p4.academicservice.model.dto.request.NewFacultyRequest;
import org.p4.academicservice.model.dto.request.UpdateFacultyRequest;
import org.p4.academicservice.model.entity.Faculty;

import java.util.List;
import java.util.UUID;

public interface FacultyService {
    Faculty getFacultyById(UUID id);
    List<Faculty> getAllFacultyMembers();
    Faculty addFaculty(NewFacultyRequest request);
    Faculty updateFaculty(UUID id, UpdateFacultyRequest request);
    void deleteFaculty(UUID id);
}
