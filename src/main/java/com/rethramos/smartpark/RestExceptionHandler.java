package com.rethramos.smartpark;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rethramos.smartpark.vehicle.exceptions.ParkingLotFullException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Void> handleNoSuchElement(NoSuchElementException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Void> handleDBConstraintViolation(DataIntegrityViolationException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(ParkingLotFullException.class)
    public ResponseEntity<BadRequestResponse> handleBusinessValidation(ParkingLotFullException e) {
        BadRequestResponse r = new BadRequestResponse();
        r.setMessages(List.of(e.getMessage()));
        return ResponseEntity.badRequest().body(r);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        // List<Map<String,Object>> errors =
        // ex.getBindingResult().getFieldErrors().stream()
        // .map(fe -> Map.of(
        // "objectName", fe.getObjectName(),
        // "field", fe.getField(),
        // "rejectedValue", fe.getRejectedValue(),
        // "message", fe.getDefaultMessage()
        // ))
        // .collect(Collectors.toList());

        // Map<String,Object> body = Map.of(
        // "timestamp", Instant.now().toString(),
        // "status", HttpStatus.BAD_REQUEST.value(),
        // "error", HttpStatus.BAD_REQUEST.getReasonPhrase(),
        // "path", request.getRequestURI(),
        // "errors", errors
        // );

        BadRequestResponse r = new BadRequestResponse();
        r.setMessages(ex.getBindingResult().getFieldErrors().stream()
                .map(e -> String.format("%s: %s", e.getField(), e.getDefaultMessage())).toList());

        return ResponseEntity.badRequest().body(r);
    }

    class BadRequestResponse {
        private List<String> messages;
        private final String error = "Bad Request";
        private final int status = 400;

        public List<String> getMessages() {
            return messages;
        }

        public void setMessages(List<String> messages) {
            this.messages = messages;
        }

        public String getError() {
            return error;
        }

        public int getStatus() {
            return status;
        }

    }
}
