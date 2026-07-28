package org.p4.academicservice.exception;

public class StudentEmailAlreadyExistsException extends RuntimeException {
    public StudentEmailAlreadyExistsException(String email){
        super("Student Email with address '" + email + "' already exists!");
    }
}
