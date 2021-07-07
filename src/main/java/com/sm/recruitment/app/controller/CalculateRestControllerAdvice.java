package com.sm.recruitment.app.controller;

import com.sm.recruitment.app.exception.NoTransactionAmountException;
import com.sm.recruitment.app.exception.NoTransactionDateException;
import com.sm.recruitment.app.model.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.NoTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static com.sm.recruitment.app.enums.InternalExceptionCode.*;
import static java.lang.String.valueOf;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ControllerAdvice
public class CalculateRestControllerAdvice extends ResponseEntityExceptionHandler {

    private static final String SEVERE = "SEVERE";

    @ExceptionHandler(NoTransactionAmountException.class)
    public ResponseEntity<AppException> mapAmountException(NoTransactionAmountException exception) {
        return ResponseEntity
                .unprocessableEntity()
                .body(new AppException(exception.getMessage(), valueOf(UNPROCESSABLE_ENTITY.value()), EXC_01, LocalDateTime.now(), SEVERE));
    }

    @ExceptionHandler(NoTransactionDateException.class)
    public ResponseEntity<AppException> mapDateException(NoTransactionDateException exception) {
        return ResponseEntity
                .unprocessableEntity()
                .body(new AppException(exception.getMessage(), valueOf(UNPROCESSABLE_ENTITY.value()), EXC_02, LocalDateTime.now(), SEVERE));
    }

    @ExceptionHandler(NoTransactionException.class)
    public ResponseEntity<AppException> mapNoTransactionException(NoTransactionException exception) {
        return ResponseEntity
                .unprocessableEntity()
                .body(new AppException(exception.getMessage(), valueOf(UNPROCESSABLE_ENTITY.value()), EXC_03, LocalDateTime.now(), SEVERE));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppException> mapException(Exception ex) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(new AppException(ex.getMessage(), valueOf(INTERNAL_SERVER_ERROR.value()), EXC_99, LocalDateTime.now(), SEVERE));
    }

}
