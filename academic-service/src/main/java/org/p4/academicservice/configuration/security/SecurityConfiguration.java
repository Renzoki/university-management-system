package org.p4.academicservice.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests(auth -> auth
                // ====== COURSES ======
                .requestMatchers(HttpMethod.GET, "/courses").authenticated()
                .requestMatchers(HttpMethod.GET, "/courses/{id}").authenticated()
                .requestMatchers(HttpMethod.GET, "/courses/code/{courseCode}").authenticated()
                .requestMatchers(HttpMethod.GET, "/courses/{courseId}/students").hasAnyRole("ADMIN", "FACULTY")
                .requestMatchers(HttpMethod.GET, "/courses/code/{courseCode}/students").hasAnyRole("ADMIN", "FACULTY")
                .requestMatchers(HttpMethod.POST, "/courses").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/courses/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/courses/{courseId}/faculty/{facultyId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/courses/{id}").hasRole("ADMIN")

                // ====== STUDENTS ====== (DONE)
                .requestMatchers(HttpMethod.GET, "/students/self").authenticated()
                .requestMatchers(HttpMethod.GET, "/students/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/students").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/students").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/students/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/students/{id}").hasRole("ADMIN")

                // ====== FACULTY ======
                .requestMatchers(HttpMethod.GET, "/faculty/self").hasRole("FACULTY")
                .requestMatchers(HttpMethod.GET, "/faculty/{facultyId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/faculty").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/faculty").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/faculty/{facultyId}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/faculty/{facultyId}").hasRole("ADMIN")

                // ====== ENROLLMENTS ======
                .requestMatchers(HttpMethod.GET, "/enrollments/students/{studentId}").authenticated()
                .requestMatchers(HttpMethod.GET, "/enrollments/courses/{courseId}").hasAnyRole("ADMIN", "FACULTY")
                .requestMatchers(HttpMethod.POST, "/enrollments/{studentId}/{courseId}").hasAnyRole("ADMIN", "FACULTY", "STUDENT")
                .requestMatchers(HttpMethod.PATCH, "/enrollments/{enrollmentId}").hasAnyRole("STUDENT", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/enrollments/{enrollmentId}").hasRole("ADMIN")

                // ====== GRADING ====== (DONE)
                .requestMatchers(HttpMethod.GET, "/grades/self/{courseId}").hasRole("STUDENT")
                .requestMatchers(HttpMethod.GET, "/grades/{studentId}/{courseId}").hasAnyRole("ADMIN", "FACULTY")
                .requestMatchers(HttpMethod.PUT, "/grades/{enrollmentId}").hasAnyRole("ADMIN, ", "FACULTY")

                .anyRequest().denyAll()
        );

        http.addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}
