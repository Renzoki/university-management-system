package org.p4.authentication.exception;

public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException() {
        super("Invalid email or password!");
    }
}
