package org.p4.academicservice.service.impl;

import org.p4.academicservice.exception.StudentEmailAlreadyExistsException;
import org.p4.academicservice.exception.StudentNotFoundException;
import org.p4.academicservice.model.dto.request.NewStudentRequest;
import org.p4.academicservice.model.dto.request.UpdateStudentRequest;
import org.p4.academicservice.model.entity.Enrollment;
import org.p4.academicservice.model.entity.Student;
import org.p4.academicservice.repository.EnrollmentRepository;
import org.p4.academicservice.repository.StudentRepository;
import org.p4.academicservice.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    public StudentServiceImpl(StudentRepository studentRepository, EnrollmentRepository enrollmentRepository){
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public Student getStudentById(UUID id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Override
    public List<Student> getAllStudents() {
        return enrollmentRepository.findAll()
                .stream()
                .map(Enrollment::getStudent)
                .toList();
    }

    @Override
    public Student addStudent(NewStudentRequest request) {
        String firstName = request.firstName();
        String lastName = request.lastName();
        String email = request.email();

        if (studentRepository.existsByEmail(request.email())) {
            throw new StudentEmailAlreadyExistsException(request.email());
        }

        Student student = new Student(firstName, lastName, email);
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(UUID id, UpdateStudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        if(request.firstName() != null){
            student.setFirstName(request.firstName());
        }

        if(request.lastName() != null){
            student.setLastName(request.lastName());
        }

        if (request.email() != null) {
            boolean changingEmail = !request.email().equals(student.getEmail());

            if (changingEmail && studentRepository.existsByEmail(request.email())) {
                throw new StudentEmailAlreadyExistsException(request.email());
            }

            student.setEmail(request.email());
        }

        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(UUID id) {
        Student student = studentRepository.findById(id)
                        .orElseThrow(() -> new StudentNotFoundException(id));

        studentRepository.delete(student);
    }
}
