package com.remcoil.timetracker.users.core.exceptions;

import com.remcoil.timetracker.core.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<MessageResponse> userNotFound(@NonNull UserNotFoundException exception) {
        return ResponseEntity.badRequest().body(new MessageResponse(exception.getMessage()));
    }
}
