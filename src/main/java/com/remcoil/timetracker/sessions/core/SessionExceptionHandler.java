package com.remcoil.timetracker.sessions.core;

import com.remcoil.timetracker.core.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SessionExceptionHandler {
    @ExceptionHandler(value = NoOpenedSessionInProjectException.class)
    public ResponseEntity<MessageResponse> noOpenedSessionFound(@NonNull NoOpenedSessionInProjectException exception) {
        return ResponseEntity.badRequest().body(new MessageResponse(exception.getMessage()));
    }

    @ExceptionHandler(value = SessionAlreadyOpenException.class)
    public ResponseEntity<MessageResponse> sessionAlreadyOpen(@NonNull SessionAlreadyOpenException exception) {
        return ResponseEntity.badRequest().body(new MessageResponse(exception.getMessage()));
    }

    @ExceptionHandler(value = NoOpenedSessionByUserException.class)
    public ResponseEntity<MessageResponse> sessionAlreadyOpen(@NonNull NoOpenedSessionByUserException exception) {
        return ResponseEntity.notFound().build();
    }
}
