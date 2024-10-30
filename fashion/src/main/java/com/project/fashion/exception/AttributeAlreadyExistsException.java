package com.project.fashion.exception;

public class AttributeAlreadyExistsException extends RuntimeException {
    public AttributeAlreadyExistsException(String message) {
        super(message);
    }

    public AttributeAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
