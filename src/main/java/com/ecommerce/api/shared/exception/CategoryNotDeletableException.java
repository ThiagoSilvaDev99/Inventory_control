package com.ecommerce.api.shared.exception;

public class CategoryNotDeletableException extends RuntimeException {
    public CategoryNotDeletableException(String message) {
        super(message);
    }
}
