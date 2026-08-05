package org.p4.academicservice.service;

import org.p4.academicservice.model.dto.request.NewStudentRequest;
import org.p4.academicservice.model.dto.request.UpdateStudentRequest;
import org.p4.academicservice.model.entity.Student;

import java.util.List;
import java.util.UUID;

public interface StudentService {
    /**
     * Retrieves a student by their unique identifier.
     *
     * @param id the unique identifier of the student
     * @return the requested student
     */
    Student getStudentById(UUID id);

    /**
     * Retrieves all students.
     *
     * @return a list of all students
     */
    List<Student> getAllStudents();

    /**
     * Creates a new student.
     *
     * @param request the details of the student to be created
     * @return the newly created student
     */
    Student addStudent(NewStudentRequest request);

    /**
     * Updates an existing student.
     *
     * @param id the unique identifier of the student to update
     * @param request the updated student details
     * @return the updated student
     */
    Student updateStudent(UUID id, UpdateStudentRequest request);

    /**
     * Deletes a student by their unique identifier.
     *
     * @param id the unique identifier of the student to delete
     */
    void deleteStudent(UUID id);
}
