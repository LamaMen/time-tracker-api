package com.remcoil.timetracker.progress.core.exceptions;

import com.remcoil.timetracker.core.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ProgressExceptionHandler {
    @ExceptionHandler(value = {
            StartDateAfterEndException.class,
            BothEndsMustBePassedException.class,
    })
    public ResponseEntity<MessageResponse> progressHandler(@NonNull RuntimeException exception) {
        return ResponseEntity.badRequest().body(new MessageResponse(exception.getMessage()));
    }
}

