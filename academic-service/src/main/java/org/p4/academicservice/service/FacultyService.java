package org.p4.academicservice.service;

import org.p4.academicservice.model.dto.request.NewFacultyRequest;
import org.p4.academicservice.model.dto.request.UpdateFacultyRequest;
import org.p4.academicservice.model.entity.Faculty;

import java.util.List;
import java.util.UUID;

public interface FacultyService {
    /**
     * Retrieves a faculty member by their unique identifier.
     *
     * @param id the unique identifier of the faculty member
     * @return the requested faculty member
     */
    Faculty getFacultyById(UUID id);

    /**
     * Retrieves all faculty members.
     *
     * @return a list of all faculty members
     */
    List<Faculty> getAllFacultyMembers();

    /**
     * Creates a new faculty member.
     *
     * @param request the details of the faculty member to be created
     * @return the newly created faculty member
     */
    Faculty addFaculty(NewFacultyRequest request);

    /**
     * Updates an existing faculty member.
     *
     * @param id the unique identifier of the faculty member to update
     * @param request the updated faculty member details
     * @return the updated faculty member
     */
    Faculty updateFaculty(UUID id, UpdateFacultyRequest request);

    /**
     * Deletes a faculty member by their unique identifier.
     *
     * @param id the unique identifier of the faculty member to delete
     */
    void deleteFaculty(UUID id);
}
