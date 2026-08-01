package org.p4.academicservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.p4.academicservice.mapper.ResponseMapper;
import org.p4.academicservice.model.dto.request.NewCourseRequest;
import org.p4.academicservice.model.dto.request.UpdateCourseRequest;
import org.p4.academicservice.model.dto.response.CourseDTO;
import org.p4.academicservice.model.dto.response.StudentDTO;
import org.p4.academicservice.model.entity.Course;
import org.p4.academicservice.model.entity.Student;
import org.p4.academicservice.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
@Validated
public class CourseController {
    private final CourseService courseService;
    private final ResponseMapper mapper;

    public CourseController(CourseService courseService, ResponseMapper mapper){
        this.courseService = courseService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable UUID id){
        return ResponseEntity.ok(
                mapper.toDto(
                        courseService.getCourseById(id)
                )
        );
    }

    @GetMapping("/code/{courseCode}")
    public ResponseEntity<CourseDTO> getCourseByCode(
            @PathVariable
            @NotBlank(message = "Course code is required!")
            @Size(min = 7, max = 7,
                    message = "Course code must be exactly 7 characters!")
            String courseCode)
    {
        return ResponseEntity.ok(
                mapper.toDto(
                        courseService.getCourseByCode(courseCode)
                )
        );
    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses(){
        return ResponseEntity.ok(
                courseService.getAllCourses()
                .stream()
                .map(mapper::toDto)
                .toList()
        );
    }

    @GetMapping("/{courseId}/students")
    public ResponseEntity<List<StudentDTO>> getStudentsByCourseId(@PathVariable UUID courseId){
        List<Student> students = courseService.getAllStudentsByCourseId(courseId);
        return ResponseEntity.ok(
                students.stream().map(mapper::toDto)
                        .toList()
        );
    }

    @GetMapping("/code/{courseCode}/students")
    public ResponseEntity<List<StudentDTO>> getStudentsByCourseCode(
            @PathVariable
            @NotBlank(message = "Course code is required!")
            @Size(min = 7, max = 7, message = "Course code must be exactly 7 characters!")
            String courseCode
    ){
        List<Student> students = courseService.getAllStudentsByCourseCode(courseCode);
        return ResponseEntity.ok(
                students.stream().map(mapper::toDto)
                        .toList()
        );
    }

    @PostMapping
    public ResponseEntity<CourseDTO> addNewCourse(@Valid @RequestBody NewCourseRequest request){
        Course course = courseService.addCourse(request);
        CourseDTO response =  mapper.toDto(course);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable UUID id,
                                                  @Valid @RequestBody UpdateCourseRequest request){
        Course course = courseService.updateCourse(id, request);
        CourseDTO response = mapper.toDto(course);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{courseId}/faculty/{facultyId}")
    public ResponseEntity<CourseDTO> assignFacultyToCourse(@PathVariable UUID courseId,
                                                           @PathVariable UUID facultyId){
        Course course = courseService.assignFacultyToCourse(courseId, facultyId);
        CourseDTO response = mapper.toDto(course);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable UUID id){
        courseService.deleteCourseById(id);
        return ResponseEntity.noContent().build();
    }

}
