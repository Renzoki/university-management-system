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

    /**
     * Handles exceptions caused by resource conflicts.
     *
     * @param ex the exception that was thrown
     * @return a {@code ResponseEntity} containing the error details
     */
    @ExceptionHandler({
            CourseAlreadyExistsException.class,
            StudentEmailAlreadyExistsException.class,
            FacultyEmailAlreadyExistsException.class,
            StudentAlreadyEnrolledInCourseException.class
    })
    public ResponseEntity<ErrorDTO> handleConflict(RuntimeException ex) {
        return error(HttpStatus.CONFLICT, ex);
    }

    /**
     * Handles exceptions caused by missing resources.
     *
     * @param ex the exception that was thrown
     * @return a {@code ResponseEntity} containing the error details
     */
    @ExceptionHandler({
            ResourceNotFoundException.class
    })
    public ResponseEntity<ErrorDTO> handleNotFound(RuntimeException ex) {
        return error(HttpStatus.NOT_FOUND, ex);
    }

    /**
     * Handles exceptions caused by unauthorized or forbidden operations.
     *
     * @param ex the exception that was thrown
     * @return a {@code ResponseEntity} containing the error details
     */
    @ExceptionHandler({
            FacultyNotAssignedToCourseException.class,
            StudentNotAuthorizedToEnrollException.class
    })
    public ResponseEntity<ErrorDTO> handleConflictState(RuntimeException ex) {
        return error(HttpStatus.FORBIDDEN, ex);
    }

    /**
     * Handles exceptions caused by invalid resource states or business rule violations.
     *
     * @param ex the exception that was thrown
     * @return a {@code ResponseEntity} containing the error details
     */
    @ExceptionHandler({
            CourseDeletionNotAllowedException.class,
            CourseHasNoFacultyAssignedException.class,
            CourseNotOfferedException.class,
            EnrollmentAlreadyCompletedException.class,
            EnrollmentAlreadyDroppedException.class,
            EnrollmentNotActiveException.class,
            EnrollmentStillActiveException.class,
            StudentNotEnrolledException.class,
            StudentNotEnrolledInCourseException.class
    })
    public ResponseEntity<ErrorDTO> handleBadRequest(RuntimeException ex) {
        return error(HttpStatus.CONFLICT, ex);
    }

    /**
     * Handles validation constraint violations on request parameters.
     *
     * @param ex the exception that was thrown
     * @return a {@code ResponseEntity} containing the validation error details
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleViolatedConstraints(ConstraintViolationException ex){
        String message = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .findFirst()
                .orElse("Validation failed.");

        return new ResponseEntity<>(new ErrorDTO(message), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles requests containing path variables of an invalid type.
     *
     * @param ex the exception that was thrown
     * @return a {@code ResponseEntity} containing the validation error details
     */
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

    /**
     * Handles validation failures for request bodies.
     *
     * @param ex the exception that was thrown
     * @return a {@code ResponseEntity} containing the validation error details
     */
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

    /**
     * Handles requests containing invalid request body values or formats.
     *
     * @param ex the exception that was thrown
     * @return a {@code ResponseEntity} containing the validation error details
     */
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

    /**
     * Creates a standardized error response.
     *
     * @param status the HTTP status of the response
     * @param ex the exception containing the error message
     * @return a {@code ResponseEntity} containing the error details
     */
    private ResponseEntity<ErrorDTO> error(HttpStatus status, RuntimeException ex) {
        return ResponseEntity.status(status)
                .body(new ErrorDTO(ex.getMessage()));
    }
}
