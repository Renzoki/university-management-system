package org.p4.authentication.service;

import org.p4.authentication.client.AcademicServiceClient;
import org.p4.authentication.client.request.NewCourseRequest;
import org.p4.authentication.client.request.NewFacultyRequest;
import org.p4.authentication.client.request.NewStudentRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.UUID;

@Service
public class SeedService {

    private final AcademicServiceClient academicServiceClient;

    public SeedService(AcademicServiceClient academicServiceClient) {
        this.academicServiceClient = academicServiceClient;
    }

    public void seedAcademicData() {
        String jwt = academicServiceClient.login();

        try {
            seedFaculty(jwt);
            seedStudents(jwt);
            seedCourses(jwt);
        } catch (WebClientResponseException.Conflict e) {
            throw new IllegalStateException("Academic database has already been seeded.");
        }
    }

    private void seedFaculty(String jwt) {
        academicServiceClient.createFaculty(jwt, new NewFacultyRequest(
                UUID.fromString("11111111-1111-1111-1111-111111111111"),
                "John",
                "Smith",
                "john.smith@dlsu.edu.ph"
        ));

        academicServiceClient.createFaculty(jwt, new NewFacultyRequest(
                UUID.fromString("22222222-2222-2222-2222-222222222222"),
                "Maria",
                "Santos",
                "maria.santos@dlsu.edu.ph"
        ));

        academicServiceClient.createFaculty(jwt, new NewFacultyRequest(
                UUID.fromString("33333333-3333-3333-3333-333333333333"),
                "James",
                "Tan",
                "james.tan@dlsu.edu.ph"
        ));
    }

    private void seedStudents(String jwt) {
        academicServiceClient.createStudent(jwt, new NewStudentRequest(
                UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
                "Juan",
                "Dela Cruz",
                "juan.delacruz@dlsu.edu.ph"
        ));

        academicServiceClient.createStudent(jwt, new NewStudentRequest(
                UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"),
                "Ana",
                "Reyes",
                "ana.reyes@dlsu.edu.ph"
        ));

        academicServiceClient.createStudent(jwt, new NewStudentRequest(
                UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc"),
                "Miguel",
                "Garcia",
                "miguel.garcia@dlsu.edu.ph"
        ));
    }

    private void seedCourses(String jwt) {
        academicServiceClient.createCourse(jwt, new NewCourseRequest(
                "Computer Programming 1",
                "CCPROG1"
        ));

        academicServiceClient.createCourse(jwt, new NewCourseRequest(
                "Algorithms and Complexity",
                "CSALGCM"
        ));

        academicServiceClient.createCourse(jwt, new NewCourseRequest(
                "Data Structures and Algorithms",
                "CCDSALG"
        ));

        academicServiceClient.createCourse(jwt, new NewCourseRequest(
                "Introduction to Intelligent Systems",
                "CSINTSY"
        ));

        academicServiceClient.createCourse(jwt, new NewCourseRequest(
                "Computer Networks",
                "CSNETWK"
        ));
    }
}