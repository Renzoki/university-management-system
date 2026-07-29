package org.p4.academicservice.configuration;

import org.p4.academicservice.model.entity.Course;
import org.p4.academicservice.model.entity.Enrollment;
import org.p4.academicservice.model.entity.Student;
import org.p4.academicservice.model.entity.enums.EnrollmentStatus;
import org.p4.academicservice.model.entity.enums.StudentStatus;
import org.p4.academicservice.repository.CourseRepository;
import org.p4.academicservice.repository.EnrollmentRepository;
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
            EnrollmentRepository enrollmentRepository
    ) {
        return args -> {

            // ====== STUDENTS ======
            if (studentRepository.count() == 0) {

                Student student2 = new Student(
                        "Cody",
                        "Ortega",
                        "student2@example.com"
                );
                student2.setId(UUID.fromString("22222222-2222-2222-2222-222222222222"));
                student2.setStatus(StudentStatus.NOT_ENROLLED);

                Student student3 = new Student(
                        "Nicole",
                        "Ortega",
                        "student3@example.com"
                );
                student3.setId(UUID.fromString("33333333-3333-3333-3333-333333333333"));
                student3.setStatus(StudentStatus.NOT_ENROLLED);

                Student student4 = new Student(
                        "Vincent Renz",
                        "Tabuzo",
                        "student4@example.com"
                );
                student4.setId(UUID.fromString("44444444-4444-4444-4444-444444444444"));
                student4.setStatus(StudentStatus.NOT_ENROLLED);

                studentRepository.save(student2);
                studentRepository.save(student3);
                studentRepository.save(student4);
            }

            // ====== COURSES ======
            if (courseRepository.count() == 0) {

                Course csadprg = new Course(
                        "Advanced Programming",
                        "CSADPRG"
                );

                Course csmodel = new Course(
                        "Modeling and Simulation",
                        "CSMODEL"
                );

                Course stadvdb = new Course(
                        "Advanced Database Systems",
                        "STADVDB"
                );

                courseRepository.save(csadprg);
                courseRepository.save(csmodel);
                courseRepository.save(stadvdb);
            }

            // ====== ENROLLMENTS ======
            if (enrollmentRepository.count() == 0) {

                Student student2 = studentRepository.findById(
                        UUID.fromString("22222222-2222-2222-2222-222222222222")
                ).orElseThrow();

                Student student3 = studentRepository.findById(
                        UUID.fromString("33333333-3333-3333-3333-333333333333")
                ).orElseThrow();

                Student student4 = studentRepository.findById(
                        UUID.fromString("44444444-4444-4444-4444-444444444444")
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