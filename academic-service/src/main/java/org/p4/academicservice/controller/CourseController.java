package org.p4.academicservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.p4.academicservice.configuration.security.AuthenticatedUser;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    /**
     * Retrieves a course by its unique identifier.
     *
     * @param courseId the unique identifier of the course to retrieve
     * @return a {@code ResponseEntity} containing the requested course as a {@link CourseDTO}
     */
    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDTO> getCourseById(
            @PathVariable UUID courseId
    ){
        return ResponseEntity.ok(
                mapper.toDto(
                        courseService.getCourseById(courseId)
                )
        );
    }

    /**
     * Retrieves a course by its course code.
     *
     * @param courseCode the unique course code of the course to retrieve
     * @return a {@code ResponseEntity} containing the requested course as a {@link CourseDTO}
     */
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

    /**
     * Retrieves all courses.
     *
     * @return a {@code ResponseEntity} containing a list of all courses as {@link CourseDTO} objects
     */
    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses(){
        return ResponseEntity.ok(
                courseService.getAllCourses()
                .stream()
                .map(mapper::toDto)
                .toList()
        );
    }

    /**
     * Retrieves all students enrolled in the specified course.
     *
     * @param employee the authenticated user requesting the enrolled students
     * @param courseId the unique identifier of the course
     * @return a {@code ResponseEntity} containing a list of enrolled students as {@link StudentDTO} objects
     */
    @GetMapping("/{courseId}/students")
    public ResponseEntity<List<StudentDTO>> getStudentsByCourseId(
            @AuthenticationPrincipal AuthenticatedUser employee,
            @PathVariable UUID courseId
    ){
        List<Student> students = courseService.getAllStudentsByCourseId(employee.id(), employee.role(), courseId);
        return ResponseEntity.ok(
                students.stream().map(mapper::toDto)
                        .toList()
        );
    }

    /**
     * Retrieves all students enrolled in the course with the specified course code.
     *
     * @param employee the authenticated user requesting the enrolled students
     * @param courseCode the unique course code of the course
     * @return a {@code ResponseEntity} containing a list of enrolled students as {@link StudentDTO} objects
     */
    @GetMapping("/code/{courseCode}/students")
    public ResponseEntity<List<StudentDTO>> getStudentsByCourseCode(
            @AuthenticationPrincipal AuthenticatedUser employee,
            @PathVariable
            @NotBlank(message = "Course code is required!")
            @Size(min = 7, max = 7, message = "Course code must be exactly 7 characters!")
            String courseCode
    ){
        List<Student> students = courseService.getAllStudentsByCourseCode(employee.id(), employee.role(), courseCode);
        return ResponseEntity.ok(
                students.stream().map(mapper::toDto)
                        .toList()
        );
    }

    /**
     * Creates a new course.
     *
     * @param request the details of the course to be created
     * @return a {@code ResponseEntity} containing the newly created course as a {@link CourseDTO}
     */
    @PostMapping
    public ResponseEntity<CourseDTO> addNewCourse(
            @Valid @RequestBody NewCourseRequest request
    ){
        Course course = courseService.addCourse(request);
        CourseDTO response =  mapper.toDto(course);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Updates an existing course.
     *
     * @param courseId the unique identifier of the course to update
     * @param request the updated course details
     * @return a {@code ResponseEntity} containing the updated course as a {@link CourseDTO}
     */
    @PutMapping("/{courseId}")
    public ResponseEntity<CourseDTO> updateCourse(
            @PathVariable UUID courseId,
            @Valid @RequestBody UpdateCourseRequest request
    ){
        Course course = courseService.updateCourse(courseId, request);
        CourseDTO response = mapper.toDto(course);

        return ResponseEntity.ok(response);
    }

    /**
     * Assigns a faculty member to a course.
     *
     * @param courseId the unique identifier of the course
     * @param facultyId the unique identifier of the faculty member to assign
     * @return a {@code ResponseEntity} containing the updated course as a {@link CourseDTO}
     */
    @PatchMapping("/{courseId}/faculty/{facultyId}")
    public ResponseEntity<CourseDTO> assignFacultyToCourse(
            @PathVariable UUID courseId,
            @PathVariable UUID facultyId
    ){
        Course course = courseService.assignFacultyToCourse(courseId, facultyId);
        CourseDTO response = mapper.toDto(course);

        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a course by its unique identifier.
     *
     * @param courseId the unique identifier of the course to delete
     * @return a {@code ResponseEntity} with no content upon successful deletion
     */
    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable UUID courseId){
        courseService.deleteCourseById(courseId);
        return ResponseEntity.noContent().build();
    }

}
