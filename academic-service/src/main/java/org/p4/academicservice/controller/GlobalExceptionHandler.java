package org.p4.academicservice.controller;

import org.p4.academicservice.exception.CourseAlreadyExistsException;
import org.p4.academicservice.exception.CourseDeletionNotAllowedException;
import org.p4.academicservice.exception.CourseNotFoundException;
import org.p4.academicservice.model.dto.response.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleCourseNotFound(CourseNotFoundException ex){
        String message = ex.getMessage();
        return new ResponseEntity<>(new ErrorDTO(message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CourseAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleCourseAlreadyExists(CourseAlreadyExistsException ex){
        String message = ex.getMessage();
        return new ResponseEntity<>(new ErrorDTO(message), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CourseDeletionNotAllowedException.class)
    public ResponseEntity<ErrorDTO> handleInvalidCourseDeletion(CourseDeletionNotAllowedException ex){
        String message = ex.getMessage();
        return new ResponseEntity<>(new ErrorDTO(message), HttpStatus.CONFLICT);
    }
}
