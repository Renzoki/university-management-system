package org.p4.authentication.configuration;

import org.p4.authentication.model.entity.User;
import org.p4.authentication.model.entity.UserRole;
import org.p4.authentication.model.entity.UserStatus;
import org.p4.authentication.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.UUID;

//THIS IS ONLY FOR THE DEMO
@Configuration
public class SeedConfiguration {

    @Bean
    CommandLineRunner seedAuthDatabase(UserRepository userRepository,
                                PasswordEncoder passwordEncoder) {
        return args -> {
            seedAdmin(userRepository, passwordEncoder);
            seedStudents(userRepository, passwordEncoder);
        };
    }

    public static void seedAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder){
        if (userRepository.findByEmail("admin@example.com").isPresent()) {
            return;
        }

        User admin = new User();
        admin.setId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
        admin.setFirstName("Renz");
        admin.setLastName("Tabuzo");
        admin.setEmail("admin@example.com");
        admin.setPasswordHash(passwordEncoder.encode("admin123"));
        admin.setRole(UserRole.ADMIN);
        admin.setStatus(UserStatus.ACTIVE);
        admin.setCreatedAt(Instant.now());
        userRepository.save(admin);
    }

    public static void seedStudents(UserRepository userRepository, PasswordEncoder passwordEncoder){
        User student2 = new User();
        student2.setId(UUID.fromString("22222222-2222-2222-2222-222222222222"));
        student2.setFirstName("Cody");
        student2.setLastName("Ortega");
        student2.setEmail("student2@example.com");
        student2.setPasswordHash(passwordEncoder.encode("student123"));
        student2.setRole(UserRole.STUDENT);
        student2.setStatus(UserStatus.ACTIVE);
        student2.setCreatedAt(Instant.now());
        userRepository.save(student2);

        User student3 = new User();
        student3.setId(UUID.fromString("33333333-3333-3333-3333-333333333333"));
        student3.setFirstName("Nicole");
        student3.setLastName("Ortega");
        student3.setEmail("student3@example.com");
        student3.setPasswordHash(passwordEncoder.encode("student123"));
        student3.setRole(UserRole.STUDENT);
        student3.setStatus(UserStatus.ACTIVE);
        student3.setCreatedAt(Instant.now());
        userRepository.save(student3);

        User student4 = new User();
        student4.setId(UUID.fromString("44444444-4444-4444-4444-444444444444"));
        student4.setFirstName("Vincent Renz");
        student4.setLastName("Tabuzo");
        student4.setEmail("student4@example.com");
        student4.setPasswordHash(passwordEncoder.encode("student123"));
        student4.setRole(UserRole.STUDENT);
        student4.setStatus(UserStatus.ACTIVE);
        student4.setCreatedAt(Instant.now());
        userRepository.save(student4);
    }
}
