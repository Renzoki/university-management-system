package org.p4.authentication.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(UUID id) {
    super("User with id '" + id + "' not found." );
  }
}
