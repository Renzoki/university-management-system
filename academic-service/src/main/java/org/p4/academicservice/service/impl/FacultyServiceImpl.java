package org.p4.academicservice.service.impl;

import org.p4.academicservice.exception.FacultyNotFoundException;
import org.p4.academicservice.model.dto.request.NewFacultyRequest;
import org.p4.academicservice.model.dto.request.UpdateFacultyRequest;
import org.p4.academicservice.model.entity.Faculty;
import org.p4.academicservice.repository.CourseRepository;
import org.p4.academicservice.repository.FacultyRepository;
import org.p4.academicservice.service.FacultyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository facultyRepository;
    private final CourseRepository courseRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository, CourseRepository courseRepository){
        this.facultyRepository = facultyRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public Faculty getFacultyById(UUID id) {
        return facultyRepository.findById(id)
                .orElseThrow(() -> FacultyNotFoundException.facultyNotFound(id));
    }

    @Override
    public List<Faculty> getAllFacultyMembers() {
        return facultyRepository.findAll();
    }

    @Override
    public Faculty addFaculty(NewFacultyRequest request) {
        Faculty faculty = new Faculty(
                request.firstName(),
                request.lastName(),
                request.email());

        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty updateFaculty(UUID id, UpdateFacultyRequest request) {
        Faculty updatedFaculty = facultyRepository.findById(id)
                .orElseThrow(() -> FacultyNotFoundException.facultyNotFound(id));

        if(request.firstName() != null){
            updatedFaculty.setFirstName(request.firstName());
        }

        if(request.lastName() != null){
            updatedFaculty.setLastName(request.lastName());
        }

        if(request.email() != null){
            updatedFaculty.setEmail(request.email());
        }

        return facultyRepository.save(updatedFaculty);
    }

    @Override
    public void deleteFaculty(UUID id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> FacultyNotFoundException.facultyNotFound(id));

        facultyRepository.delete(faculty);
    }
}
