package com.github.gatoke.offers.port.adapter.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler
    ResponseEntity<List<ValidationError>> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        final List<ValidationError> validationErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(toList());
        return status(BAD_REQUEST).body(validationErrors);
    }

    @Getter
    @RequiredArgsConstructor
    private static class ValidationError {

        private final String field;
        private final String cause;
    }
}
