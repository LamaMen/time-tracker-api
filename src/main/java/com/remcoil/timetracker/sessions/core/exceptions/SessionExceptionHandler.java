package com.remcoil.timetracker.sessions.core.exceptions;

import com.remcoil.timetracker.core.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SessionExceptionHandler {
    @ExceptionHandler(value = {NoOpenedSessionInProjectException.class, SessionAlreadyOpenException.class})
    public ResponseEntity<MessageResponse> noOpenedSessionFound(@NonNull RuntimeException exception) {
        return ResponseEntity.badRequest().body(new MessageResponse(exception.getMessage()));
    }
}
