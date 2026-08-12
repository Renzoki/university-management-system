package org.p4.authentication.client;

import org.p4.authentication.client.request.NewCourseRequest;
import org.p4.authentication.client.request.NewFacultyRequest;
import org.p4.authentication.client.request.NewStudentRequest;
import org.p4.authentication.client.response.CourseResponse;
import org.p4.authentication.model.dto.LoginRequest;
import org.p4.authentication.model.dto.LoginResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Component
public class AcademicServiceClient {

    private static final String AUTH_SERVICE_URL = "http://localhost:8080";
    private static final String ACADEMIC_SERVICE_URL = "http://localhost:8081";

    private static final String LOGIN_ENDPOINT = "/auth/login";
    private static final String STUDENTS_ENDPOINT = "/students";
    private static final String FACULTY_ENDPOINT = "/faculty";
    private static final String COURSES_ENDPOINT = "/courses";
    private static final String ENROLLMENTS_ENDPOINT = "/enrollments";

    private final WebClient authWebClient;
    private final WebClient academicWebClient;

    public AcademicServiceClient() {
        this.authWebClient = WebClient.builder()
                .baseUrl(AUTH_SERVICE_URL)
                .build();

        this.academicWebClient = WebClient.builder()
                .baseUrl(ACADEMIC_SERVICE_URL)
                .build();
    }

    public String login() {
        LoginResponse response = authWebClient.post()
                .uri(LOGIN_ENDPOINT)
                .bodyValue(new LoginRequest(
                        "admin@example.com",
                        "admin123"
                ))
                .retrieve()
                .bodyToMono(LoginResponse.class)
                .block();

        return response.accessToken();
    }

    public void createStudent(
            String jwt,
            NewStudentRequest request
    ) {
        academicWebClient.post()
                .uri(STUDENTS_ENDPOINT)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void createFaculty(
            String jwt,
            NewFacultyRequest request
    ) {
        academicWebClient.post()
                .uri(FACULTY_ENDPOINT)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public CourseResponse createCourse(
            String jwt,
            NewCourseRequest request
    ) {
        return academicWebClient.post()
                .uri(COURSES_ENDPOINT)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(CourseResponse.class)
                .block();
    }

    public void assignFacultyToCourse(
            String jwt,
            UUID courseId,
            UUID facultyId
    ) {
        academicWebClient.patch()
                .uri(
                        COURSES_ENDPOINT + "/{courseId}/faculty/{facultyId}",
                        courseId,
                        facultyId
                )
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    public void enrollStudent(
            String jwt,
            UUID studentId,
            UUID courseId
    ) {
        academicWebClient.post()
                .uri(
                        ENROLLMENTS_ENDPOINT + "/{studentId}/{courseId}",
                        studentId,
                        courseId
                )
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}