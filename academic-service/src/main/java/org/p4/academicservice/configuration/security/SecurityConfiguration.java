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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests(auth -> auth

                        // ====== CORS PREFLIGHT ======
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // ====== COURSES ======
                        .requestMatchers(HttpMethod.GET, "/courses").authenticated()
                        .requestMatchers(HttpMethod.GET, "/courses/{courseId}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/courses/code/{courseCode}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/courses/{courseId}/students")
                        .hasAnyRole("ADMIN", "FACULTY")
                        .requestMatchers(HttpMethod.GET, "/courses/code/{courseCode}/students")
                        .hasAnyRole("ADMIN", "FACULTY")
                        .requestMatchers(HttpMethod.POST, "/courses").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/courses/{courseId}").hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/courses/{courseId}/faculty/{facultyId}"
                        ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/courses/{courseId}").hasRole("ADMIN")

                        // ====== STUDENTS ======
                        .requestMatchers(HttpMethod.GET, "/students/self").authenticated()
                        .requestMatchers(HttpMethod.GET, "/students/{studentId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/students").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/students").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/students/{studentId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/students/{studentId}").hasRole("ADMIN")

                        // ====== FACULTY ======
                        .requestMatchers(HttpMethod.GET, "/faculty/self").hasRole("FACULTY")
                        .requestMatchers(HttpMethod.GET, "/faculty/{facultyId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/faculty").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/faculty").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/faculty/{facultyId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/faculty/{facultyId}").hasRole("ADMIN")

                        // ====== ENROLLMENTS ======
                        .requestMatchers(
                                HttpMethod.GET,
                                "/enrollments/student/self"
                        ).hasRole("STUDENT")
                        .requestMatchers(
                                HttpMethod.GET,
                                "/enrollments/students/{studentId}"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.GET,
                                "/enrollments/courses/{courseId}"
                        ).hasAnyRole("ADMIN", "FACULTY")
                        .requestMatchers(
                                HttpMethod.POST,
                                "/enrollments/self/{courseId}"
                        ).hasRole("STUDENT")
                        .requestMatchers(
                                HttpMethod.POST,
                                "/enrollments/{studentId}/{courseId}"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/enrollments/self/{enrollmentId}"
                        ).hasRole("STUDENT")
                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/enrollments/{studentId}/{enrollmentId}"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/enrollments/{enrollmentId}"
                        ).hasRole("ADMIN")

                        // ====== GRADING ======
                        .requestMatchers(
                                HttpMethod.GET,
                                "/grades/self/{courseId}"
                        ).hasRole("STUDENT")
                        .requestMatchers(
                                HttpMethod.GET,
                                "/grades/{studentId}/{courseId}"
                        ).hasAnyRole("ADMIN", "FACULTY")
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/grades/{enrollmentId}"
                        ).hasAnyRole("ADMIN", "FACULTY")

                        .anyRequest().denyAll()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:5173")
        );

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "PATCH",
                        "DELETE",
                        "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                List.of(
                        "Authorization",
                        "Content-Type"
                )
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}