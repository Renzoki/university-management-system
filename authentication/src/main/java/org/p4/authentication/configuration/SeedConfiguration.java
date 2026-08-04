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
            seedFaculty(userRepository, passwordEncoder);
        };
    }

    public static void seedAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder){
        if (userRepository.findByEmail("admin@example.com").isPresent()) {
            return;
        }

        User admin = new User();
        admin.setId(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        admin.setFirstName("Renz");
        admin.setLastName("Tabuzo");
        admin.setEmail("admin@example.com");
        admin.setPasswordHash(passwordEncoder.encode("admin123"));
        admin.setRole(UserRole.ADMIN);
        admin.setStatus(UserStatus.ACTIVE);
        admin.setCreatedAt(Instant.now());
        userRepository.save(admin);
    }

    private static void seedStudents(UserRepository userRepository,
                                     PasswordEncoder passwordEncoder) {

        if (userRepository.findByEmail("juan.delacruz@dlsu.edu.ph").isPresent()) {
            return;
        }

        User student1 = new User();
        student1.setId(UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"));
        student1.setFirstName("Juan");
        student1.setLastName("Dela Cruz");
        student1.setEmail("juan.delacruz@dlsu.edu.ph");
        student1.setPasswordHash(passwordEncoder.encode("password123"));
        student1.setRole(UserRole.STUDENT);
        student1.setStatus(UserStatus.ACTIVE);
        student1.setCreatedAt(Instant.now());
        userRepository.save(student1);

        User student2 = new User();
        student2.setId(UUID.fromString("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"));
        student2.setFirstName("Ana");
        student2.setLastName("Reyes");
        student2.setEmail("ana.reyes@dlsu.edu.ph");
        student2.setPasswordHash(passwordEncoder.encode("password123"));
        student2.setRole(UserRole.STUDENT);
        student2.setStatus(UserStatus.ACTIVE);
        student2.setCreatedAt(Instant.now());
        userRepository.save(student2);

        User student3 = new User();
        student3.setId(UUID.fromString("cccccccc-cccc-cccc-cccc-cccccccccccc"));
        student3.setFirstName("Miguel");
        student3.setLastName("Garcia");
        student3.setEmail("miguel.garcia@dlsu.edu.ph");
        student3.setPasswordHash(passwordEncoder.encode("password123"));
        student3.setRole(UserRole.STUDENT);
        student3.setStatus(UserStatus.ACTIVE);
        student3.setCreatedAt(Instant.now());
        userRepository.save(student3);
    }

    private static void seedFaculty(UserRepository userRepository,
                                    PasswordEncoder passwordEncoder) {

        if (userRepository.findByEmail("john.smith@dlsu.edu.ph").isPresent()) {
            return;
        }

        User faculty1 = new User();
        faculty1.setId(UUID.fromString("11111111-1111-1111-1111-111111111111"));
        faculty1.setFirstName("John");
        faculty1.setLastName("Smith");
        faculty1.setEmail("john.smith@dlsu.edu.ph");
        faculty1.setPasswordHash(passwordEncoder.encode("password123"));
        faculty1.setRole(UserRole.FACULTY);
        faculty1.setStatus(UserStatus.ACTIVE);
        faculty1.setCreatedAt(Instant.now());
        userRepository.save(faculty1);

        User faculty2 = new User();
        faculty2.setId(UUID.fromString("22222222-2222-2222-2222-222222222222"));
        faculty2.setFirstName("Maria");
        faculty2.setLastName("Santos");
        faculty2.setEmail("maria.santos@dlsu.edu.ph");
        faculty2.setPasswordHash(passwordEncoder.encode("password123"));
        faculty2.setRole(UserRole.FACULTY);
        faculty2.setStatus(UserStatus.ACTIVE);
        faculty2.setCreatedAt(Instant.now());
        userRepository.save(faculty2);

        User faculty3 = new User();
        faculty3.setId(UUID.fromString("33333333-3333-3333-3333-333333333333"));
        faculty3.setFirstName("James");
        faculty3.setLastName("Tan");
        faculty3.setEmail("james.tan@dlsu.edu.ph");
        faculty3.setPasswordHash(passwordEncoder.encode("password123"));
        faculty3.setRole(UserRole.FACULTY);
        faculty3.setStatus(UserStatus.ACTIVE);
        faculty3.setCreatedAt(Instant.now());
        userRepository.save(faculty3);
    }
}
