package org.p4.academicservice.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.p4.academicservice.exception.*;
import org.p4.academicservice.model.dto.response.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import tools.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleCourseNotFound(CourseNotFoundException ex){
        String message = ex.getMessage();
        return new ResponseEntity<>(new ErrorDTO(message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleStudentNotFound(StudentNotFoundException ex){
        String message = ex.getMessage();
        return new ResponseEntity<>(new ErrorDTO(message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EnrollmentNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleEnrollmentNotFound(EnrollmentNotFoundException ex){
        String message = ex.getMessage();
        return new ResponseEntity<>(new ErrorDTO(message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CourseAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleCourseAlreadyExists(CourseAlreadyExistsException ex){
        String message = ex.getMessage();
        return new ResponseEntity<>(new ErrorDTO(message), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(StudentEmailAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleStudentEmailAlreadyExists(StudentEmailAlreadyExistsException ex){
        String message = ex.getMessage();
        return new ResponseEntity<>(new ErrorDTO(message), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EnrollmentAlreadyCompletedException.class)
    public ResponseEntity<ErrorDTO> handleEnrollmentAlreadyCompleted(EnrollmentAlreadyCompletedException ex){
        String message = ex.getMessage();
        return new ResponseEntity<>(new ErrorDTO(message), HttpStatus.CONFLICT);
    }


    @ExceptionHandler(CourseDeletionNotAllowedException.class)
    public ResponseEntity<ErrorDTO> handleInvalidCourseDeletion(CourseDeletionNotAllowedException ex){
        String message = ex.getMessage();
        return new ResponseEntity<>(new ErrorDTO(message), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(StudentNotEnrolledException.class)
    public ResponseEntity<ErrorDTO> handleUnenrolledStudentTryingToEnroll(StudentNotEnrolledException ex){
        String message = ex.getMessage();
        return new ResponseEntity<>(new ErrorDTO(message), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EnrollmentStillActiveException.class)
    public ResponseEntity<ErrorDTO> handleInvalidEnrollmentDeletion(EnrollmentStillActiveException ex){
        String message = ex.getMessage();
        return new ResponseEntity<>(new ErrorDTO(message), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CourseNotOfferedException.class)
    public ResponseEntity<ErrorDTO> handleTryingToEnrollInUnofferedCourse(CourseNotOfferedException ex){
        String message = ex.getMessage();
        return new ResponseEntity<>(new ErrorDTO(message), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleViolatedConstraints(ConstraintViolationException ex){
        String message = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse("Validation failed.");

        return new ResponseEntity<>(new ErrorDTO(message), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDTO> handleInvalidPathVariable(MethodArgumentTypeMismatchException ex){
        String expectedType = ex.getRequiredType() != null
                ? ex.getRequiredType().getSimpleName()
                : "valid type";

        String message = String.format(
                "Parameter '%s' must be a %s.",
                ex.getName(),
                expectedType
        );

        return new ResponseEntity<>(new ErrorDTO(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .findFirst()
                .orElse("Validation failed.");

        return ResponseEntity.badRequest()
                .body(new ErrorDTO(message));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDTO> handleInvalidEnum(
            HttpMessageNotReadableException ex) {

        String message = "Invalid request format.";

        if (ex.getCause() instanceof InvalidFormatException invalidFormatException) {
            if (invalidFormatException.getTargetType().isEnum()) {
                message = String.format(
                        "Invalid value '%s' for %s.",
                        invalidFormatException.getValue(),
                        invalidFormatException.getTargetType().getSimpleName()
                );
            }
        }

        return ResponseEntity.badRequest()
                .body(new ErrorDTO(message));
    }
}
