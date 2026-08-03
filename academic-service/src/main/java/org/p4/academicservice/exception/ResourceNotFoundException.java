package org.p4.academicservice.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, String field, Object value) {
        super(resource + " with " + field + " '" + value + "' not found!");
    }
}
