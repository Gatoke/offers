package com.github.gatoke.offers.port.adapter.configuration;

import com.github.gatoke.offers.domain.offer.exception.InvalidOfferStatusStateException;
import com.github.gatoke.offers.domain.offer.exception.OfferNotFoundException;
import com.github.gatoke.offers.domain.offer.exception.UnknownOfferStatusException;
import com.github.gatoke.offers.domain.user.exception.InvalidEmailException;
import com.github.gatoke.offers.domain.user.exception.InvalidNameException;
import com.github.gatoke.offers.domain.user.exception.UserAlreadyExistsException;
import com.github.gatoke.offers.domain.user.exception.UserNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;
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

    @ExceptionHandler
    ResponseEntity<String> handleInvalidOfferStatusStateException(final InvalidOfferStatusStateException ex) {
        return status(BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleUserNotFoundException(final UserNotFoundException ex) {
        return status(NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleOfferNotFoundException(final OfferNotFoundException ex) {
        return status(NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleUnknownOfferStatusException(final UnknownOfferStatusException ex) {
        return status(BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleInvalidNameException(final InvalidNameException ex) {
        return status(BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleInvalidEmailException(final InvalidEmailException ex) {
        return status(BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleUserAlreadyExistsException(final UserAlreadyExistsException ex) {
        return status(CONFLICT).body(ex.getMessage());
    }

    @Getter
    @RequiredArgsConstructor
    private static class ValidationError {

        private final String field;
        private final String cause;
    }
}
