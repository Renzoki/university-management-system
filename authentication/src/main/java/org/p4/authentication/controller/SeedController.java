package org.p4.authentication.controller;

import org.p4.authentication.service.SeedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SeedController {

    private final SeedService seedService;

    public SeedController(SeedService seedService) {
        this.seedService = seedService;
    }

    @PostMapping("/seed/academic")
    public ResponseEntity<String> seedAcademicData() {
        seedService.seedAcademicData();
        return ResponseEntity.ok("Academic service seeded successfully.");
    }
}