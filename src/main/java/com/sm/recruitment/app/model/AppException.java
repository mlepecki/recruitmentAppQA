package com.sm.recruitment.app.model;

import com.sm.recruitment.app.enums.InternalExceptionCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class AppException {
    private String id;
    private String httpCode;
    private InternalExceptionCode internalCode;
    private String message;
    private LocalDateTime timestamp;
    private String severity;

    public AppException(String message, String httpCode, InternalExceptionCode internalCode, LocalDateTime timestamp, String severity) {
        this.id = UUID.randomUUID().toString();
        this.httpCode = httpCode;
        this.internalCode = internalCode;
        this.message = message;
        this.timestamp = timestamp;
        this.severity = severity;
    }
}
