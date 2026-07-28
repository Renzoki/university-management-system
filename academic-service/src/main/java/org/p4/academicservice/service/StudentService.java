package org.p4.academicservice.service;

import org.p4.academicservice.model.dto.request.NewStudentRequest;
import org.p4.academicservice.model.dto.request.UpdateStudentRequest;
import org.p4.academicservice.model.entity.Student;

import java.util.List;
import java.util.UUID;

public interface StudentService {
    Student getStudentById(UUID id);
    List<Student> getAllStudents();
    Student addStudent(NewStudentRequest request);
    Student updateStudent(UUID id, UpdateStudentRequest request);
    void deleteStudent(UUID id);
}
