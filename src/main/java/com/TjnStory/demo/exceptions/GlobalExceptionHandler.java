package com.TjnStory.demo.exceptions;

import com.TjnStory.demo.DTO.StandardErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ProductNotFoundException.class, CategoryNotFoundException.class})
    public ResponseEntity<StandardErrorDTO> handleResourceNotFound(RuntimeException ex, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;

        StandardErrorDTO error = new StandardErrorDTO(
                Instant.now(),
                status.value(),
                "Resource not found",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler({ProductValidationException.class,CategoryValidationException.class})
    public ResponseEntity<StandardErrorDTO> handleBusinessRules(RuntimeException ex, HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNPROCESSABLE_CONTENT;

        StandardErrorDTO error = new StandardErrorDTO(
                Instant.now(),
                status.value(),
                "Business rule violation",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler({ProductAlreadyExistsException.class,CategoryAlreadyExistsException.class,CategoryNotDeletableException.class})
    public ResponseEntity<StandardErrorDTO> handleConflict(RuntimeException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        StandardErrorDTO error = new StandardErrorDTO(
                Instant.now(),
                status.value(),
                "Resource conflict",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(error);
    }
}
