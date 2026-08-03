package org.p4.academicservice.exception;

public class FacultyEmailAlreadyExistsException extends RuntimeException {
    public FacultyEmailAlreadyExistsException(String email){
        super("Faculty Email with address '" + email + "' already exists!");
    }
}
