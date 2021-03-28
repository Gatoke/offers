package com.github.gatoke.offers.port.adapter.rest;

import com.github.gatoke.offers.domain.offer.exception.InvalidOfferStatusStateException;
import com.github.gatoke.offers.domain.offer.exception.OfferNotFoundException;
import com.github.gatoke.offers.domain.offer.exception.UnknownOfferStatusException;
import com.github.gatoke.offers.domain.user.exception.InvalidEmailException;
import com.github.gatoke.offers.domain.user.exception.InvalidNameException;
import com.github.gatoke.offers.domain.user.exception.UserAlreadyExistsException;
import com.github.gatoke.offers.domain.user.exception.UserNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@Slf4j
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler
    ResponseEntity<List<ValidationError>> handleMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        final List<ValidationError> validationErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(toList());
        log.debug(ex.getMessage());
        return status(BAD_REQUEST).body(validationErrors);
    }

    @ExceptionHandler
    ResponseEntity<String> handleInvalidOfferStatusStateException(final InvalidOfferStatusStateException ex) {
        log.debug(ex.getMessage());
        return status(BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleUserNotFoundException(final UserNotFoundException ex) {
        log.debug(ex.getMessage());
        return status(NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleOfferNotFoundException(final OfferNotFoundException ex) {
        log.debug(ex.getMessage());
        return status(NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleUnknownOfferStatusException(final UnknownOfferStatusException ex) {
        log.debug(ex.getMessage());
        return status(BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleInvalidNameException(final InvalidNameException ex) {
        log.debug(ex.getMessage());
        return status(BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleInvalidEmailException(final InvalidEmailException ex) {
        log.debug(ex.getMessage());
        return status(BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleUserAlreadyExistsException(final UserAlreadyExistsException ex) {
        log.debug(ex.getMessage());
        return status(CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> handleGenericException(final Exception ex) {
        log.warn(ex.getMessage());
        return status(INTERNAL_SERVER_ERROR).body(INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    @Getter
    @RequiredArgsConstructor
    private static class ValidationError {

        private final String field;
        private final String cause;
    }
}
