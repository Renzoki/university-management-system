package org.p4.academicservice.configuration;

import org.p4.academicservice.model.entity.Course;
import org.p4.academicservice.model.entity.Enrollment;
import org.p4.academicservice.model.entity.Faculty;
import org.p4.academicservice.model.entity.Student;
import org.p4.academicservice.model.entity.enums.EnrollmentStatus;
import org.p4.academicservice.model.entity.enums.FacultyStatus;
import org.p4.academicservice.model.entity.enums.StudentStatus;
import org.p4.academicservice.repository.CourseRepository;
import org.p4.academicservice.repository.EnrollmentRepository;
import org.p4.academicservice.repository.FacultyRepository;
import org.p4.academicservice.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class SeedConfiguration {

    @Bean
    CommandLineRunner seedAcademicDatabase(
            CourseRepository courseRepository,
            StudentRepository studentRepository,
            EnrollmentRepository enrollmentRepository,
            FacultyRepository facultyRepository
    ) {
        return args -> {

            // ====== FACULTY ======
            if (facultyRepository.count() == 0) {

                Faculty faculty1 = new Faculty(
                        "John",
                        "Dela Cruz",
                        "faculty1@example.com"
                );
                faculty1.setId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
                faculty1.setStatus(FacultyStatus.ACTIVE);

                Faculty faculty2 = new Faculty(
                        "Maria",
                        "Santos",
                        "faculty2@example.com"
                );
                faculty2.setId(UUID.fromString("22222222-2222-2222-2222-222222222222"));
                faculty2.setStatus(FacultyStatus.ACTIVE);

                facultyRepository.save(faculty1);
                facultyRepository.save(faculty2);
            }

            // ====== STUDENTS ======
            if (studentRepository.count() == 0) {

                Student student2 = new Student(
                        "Cody",
                        "Ortega",
                        "student2@example.com"
                );
                student2.setId(UUID.fromString("33333333-3333-3333-3333-333333333333"));
                student2.setStatus(StudentStatus.NOT_ENROLLED);

                Student student3 = new Student(
                        "Nicole",
                        "Ortega",
                        "student3@example.com"
                );
                student3.setId(UUID.fromString("44444444-4444-4444-4444-444444444444"));
                student3.setStatus(StudentStatus.NOT_ENROLLED);

                Student student4 = new Student(
                        "Vincent Renz",
                        "Tabuzo",
                        "student4@example.com"
                );
                student4.setId(UUID.fromString("55555555-5555-5555-5555-555555555555"));
                student4.setStatus(StudentStatus.NOT_ENROLLED);

                studentRepository.save(student2);
                studentRepository.save(student3);
                studentRepository.save(student4);
            }

            // ====== COURSES ======
            if (courseRepository.count() == 0) {

                Faculty faculty1 = facultyRepository.findById(
                        UUID.fromString("11111111-1111-1111-1111-111111111111")
                ).orElseThrow();

                Faculty faculty2 = facultyRepository.findById(
                        UUID.fromString("22222222-2222-2222-2222-222222222222")
                ).orElseThrow();

                Course csadprg = new Course(
                        "Advanced Programming",
                        "CSADPRG"
                );
                csadprg.setFaculty(faculty1);

                Course csmodel = new Course(
                        "Modeling and Simulation",
                        "CSMODEL"
                );
                csmodel.setFaculty(faculty1);

                Course stadvdb = new Course(
                        "Advanced Database Systems",
                        "STADVDB"
                );
                stadvdb.setFaculty(faculty2);

                courseRepository.save(csadprg);
                courseRepository.save(csmodel);
                courseRepository.save(stadvdb);
            }

            // ====== ENROLLMENTS ======
            if (enrollmentRepository.count() == 0) {

                Student student2 = studentRepository.findById(
                        UUID.fromString("33333333-3333-3333-3333-333333333333")
                ).orElseThrow();

                Student student3 = studentRepository.findById(
                        UUID.fromString("44444444-4444-4444-4444-444444444444")
                ).orElseThrow();

                Student student4 = studentRepository.findById(
                        UUID.fromString("55555555-5555-5555-5555-555555555555")
                ).orElseThrow();

                Course csadprg = courseRepository.findByCourseCode("CSADPRG")
                        .orElseThrow();

                Course csmodel = courseRepository.findByCourseCode("CSMODEL")
                        .orElseThrow();

                Course stadvdb = courseRepository.findByCourseCode("STADVDB")
                        .orElseThrow();

                Enrollment e1 = new Enrollment();
                e1.setStudent(student2);
                e1.setCourse(csadprg);
                e1.setStatus(EnrollmentStatus.ACTIVE);

                Enrollment e2 = new Enrollment();
                e2.setStudent(student2);
                e2.setCourse(csmodel);
                e2.setStatus(EnrollmentStatus.ACTIVE);

                Enrollment e3 = new Enrollment();
                e3.setStudent(student3);
                e3.setCourse(csadprg);
                e3.setStatus(EnrollmentStatus.ACTIVE);

                Enrollment e4 = new Enrollment();
                e4.setStudent(student4);
                e4.setCourse(stadvdb);
                e4.setStatus(EnrollmentStatus.ACTIVE);

                enrollmentRepository.save(e1);
                enrollmentRepository.save(e2);
                enrollmentRepository.save(e3);
                enrollmentRepository.save(e4);
            }
        };
    }
}