package com.semicolon.EaziRent.handlers;

import com.semicolon.EaziRent.dtos.responses.ErrorResponse;
import com.semicolon.EaziRent.exceptions.EmailExistsException;
import com.semicolon.EaziRent.exceptions.ResourceNotFoundException;
import com.semicolon.EaziRent.exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

import static java.time.LocalDateTime.now;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException exception,
                                                        HttpServletRequest request) {
        ErrorResponse response = ErrorResponse.builder()
                .requestTime(now())
                .success(false)
                .error("IllegalState")
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<?> handleEmailExistsException(EmailExistsException exception,
                                                        HttpServletRequest request) {
        ErrorResponse response = ErrorResponse.builder()
                .requestTime(now())
                .success(false)
                .error("EmailExists")
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> handleIOException(IOException exception,
                                                        HttpServletRequest request) {
        ErrorResponse response = ErrorResponse.builder()
                .requestTime(now())
                .success(false)
                .error("UploadMediaFailed")
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException exception,
                                                        HttpServletRequest request) {
        ErrorResponse response = ErrorResponse.builder()
                .requestTime(now())
                .success(false)
                .error("UserNotFound")
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                        HttpServletRequest request) {
        ErrorResponse response = ErrorResponse.builder()
                .requestTime(now())
                .success(false)
                .error("ResourceNotFound")
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.badRequest().body(response);
    }

}
