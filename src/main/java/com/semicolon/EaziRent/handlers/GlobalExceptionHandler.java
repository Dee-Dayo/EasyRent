package com.semicolon.EaziRent.handlers;

import com.semicolon.EaziRent.dtos.responses.ErrorResponse;
import com.semicolon.EaziRent.exceptions.EmailExistsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
                .error("UsernameExists")
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.badRequest().body(response);
    }
}
