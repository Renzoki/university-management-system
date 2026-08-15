package org.p4.authentication.service;

import org.p4.authentication.client.AcademicServiceClient;
import org.p4.authentication.client.request.NewCourseRequest;
import org.p4.authentication.client.request.NewFacultyRequest;
import org.p4.authentication.client.request.NewStudentRequest;
import org.p4.authentication.client.response.CourseResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.UUID;

@Service
public class SeedService {

    private static final UUID JOHN_SMITH_ID =
            UUID.fromString("11111111-1111-1111-1111-111111111111");

    private static final UUID MARIA_SANTOS_ID =
            UUID.fromString("22222222-2222-2222-2222-222222222222");

    private static final UUID JAMES_TAN_ID =
            UUID.fromString("33333333-3333-3333-3333-333333333333");

    private static final UUID JUAN_DELA_CRUZ_ID =
            UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

    private static final UUID ANA_REYES_ID =
            UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");

    private static final UUID MIGUEL_GARCIA_ID =
            UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc");

    private final AcademicServiceClient academicServiceClient;

    public SeedService(AcademicServiceClient academicServiceClient) {
        this.academicServiceClient = academicServiceClient;
    }

    public void seedAcademicData() {
        String jwt = academicServiceClient.login();

        try {
            seedFaculty(jwt);
            seedStudents(jwt);

            CourseResponse ccprog1 = academicServiceClient.createCourse(
                    jwt,
                    new NewCourseRequest(
                            "Computer Programming 1",
                            "CCPROG1"
                    )
            );

            CourseResponse csalgcm = academicServiceClient.createCourse(
                    jwt,
                    new NewCourseRequest(
                            "Algorithms and Complexity",
                            "CSALGCM"
                    )
            );

            CourseResponse ccdsalg = academicServiceClient.createCourse(
                    jwt,
                    new NewCourseRequest(
                            "Data Structures and Algorithms",
                            "CCDSALG"
                    )
            );

            CourseResponse csintsy = academicServiceClient.createCourse(
                    jwt,
                    new NewCourseRequest(
                            "Introduction to Intelligent Systems",
                            "CSINTSY"
                    )
            );

            CourseResponse csnetwk = academicServiceClient.createCourse(
                    jwt,
                    new NewCourseRequest(
                            "Computer Networks",
                            "CSNETWK"
                    )
            );

            assignFaculty(
                    jwt,
                    ccprog1,
                    csalgcm,
                    ccdsalg,
                    csintsy,
                    csnetwk
            );

            enrollStudents(
                    jwt,
                    ccprog1,
                    csalgcm,
                    ccdsalg,
                    csintsy
            );

        } catch (WebClientResponseException.Conflict e) {
            throw new IllegalStateException(
                    "Academic database has already been seeded."
            );
        }
    }

    private void seedFaculty(String jwt) {

        academicServiceClient.createFaculty(
                jwt,
                new NewFacultyRequest(
                        JOHN_SMITH_ID,
                        "John",
                        "Smith",
                        "john.smith@dlsu.edu.ph"
                )
        );

        academicServiceClient.createFaculty(
                jwt,
                new NewFacultyRequest(
                        MARIA_SANTOS_ID,
                        "Maria",
                        "Santos",
                        "maria.santos@dlsu.edu.ph"
                )
        );

        academicServiceClient.createFaculty(
                jwt,
                new NewFacultyRequest(
                        JAMES_TAN_ID,
                        "James",
                        "Tan",
                        "james.tan@dlsu.edu.ph"
                )
        );
    }

    private void seedStudents(String jwt) {

        academicServiceClient.createStudent(
                jwt,
                new NewStudentRequest(
                        JUAN_DELA_CRUZ_ID,
                        "Juan",
                        "Dela Cruz",
                        "juan.delacruz@dlsu.edu.ph"
                )
        );

        academicServiceClient.createStudent(
                jwt,
                new NewStudentRequest(
                        ANA_REYES_ID,
                        "Ana",
                        "Reyes",
                        "ana.reyes@dlsu.edu.ph"
                )
        );

        academicServiceClient.createStudent(
                jwt,
                new NewStudentRequest(
                        MIGUEL_GARCIA_ID,
                        "Miguel",
                        "Garcia",
                        "miguel.garcia@dlsu.edu.ph"
                )
        );
    }

    private void assignFaculty(
            String jwt,
            CourseResponse ccprog1,
            CourseResponse csalgcm,
            CourseResponse ccdsalg,
            CourseResponse csintsy,
            CourseResponse csnetwk
    ) {

        // John Smith
        academicServiceClient.assignFacultyToCourse(
                jwt,
                ccprog1.id(),
                JOHN_SMITH_ID
        );

        academicServiceClient.assignFacultyToCourse(
                jwt,
                csintsy.id(),
                JOHN_SMITH_ID
        );

        // Maria Santos
        academicServiceClient.assignFacultyToCourse(
                jwt,
                csalgcm.id(),
                MARIA_SANTOS_ID
        );

        academicServiceClient.assignFacultyToCourse(
                jwt,
                csnetwk.id(),
                MARIA_SANTOS_ID
        );

        // James Tan
        academicServiceClient.assignFacultyToCourse(
                jwt,
                ccdsalg.id(),
                JAMES_TAN_ID
        );
    }

    private void enrollStudents(
            String jwt,
            CourseResponse ccprog1,
            CourseResponse csalgcm,
            CourseResponse ccdsalg,
            CourseResponse csintsy
    ) {

        // Juan Dela Cruz
        UUID juanCcprog1EnrollmentId =
                academicServiceClient.enrollStudent(
                        jwt,
                        JUAN_DELA_CRUZ_ID,
                        ccprog1.id()
                );

        academicServiceClient.enrollStudent(
                jwt,
                JUAN_DELA_CRUZ_ID,
                csalgcm.id()
        );

        UUID juanCsintsyEnrollmentId =
                academicServiceClient.enrollStudent(
                        jwt,
                        JUAN_DELA_CRUZ_ID,
                        csintsy.id()
                );

        // Ana Reyes
        academicServiceClient.enrollStudent(
                jwt,
                ANA_REYES_ID,
                ccprog1.id()
        );

        UUID anaCcdsalgEnrollmentId =
                academicServiceClient.enrollStudent(
                        jwt,
                        ANA_REYES_ID,
                        ccdsalg.id()
                );

        academicServiceClient.enrollStudent(
                jwt,
                ANA_REYES_ID,
                csintsy.id()
        );

        academicServiceClient.assignGrade(
                jwt,
                juanCcprog1EnrollmentId,
                85
        );

        academicServiceClient.assignGrade(
                jwt,
                juanCsintsyEnrollmentId,
                92
        );

        academicServiceClient.assignGrade(
                jwt,
                anaCcdsalgEnrollmentId,
                78
        );
    }
}