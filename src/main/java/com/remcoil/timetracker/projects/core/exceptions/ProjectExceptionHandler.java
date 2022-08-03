package com.remcoil.timetracker.projects.core.exceptions;


import com.remcoil.timetracker.core.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProjectExceptionHandler {
    @ExceptionHandler(value = {
            ProjectNotFoundException.class,
            ProjectAlreadyArchivedException.class,
            ProjectArchiveException.class,
    })
    public ResponseEntity<MessageResponse> projectNotFound(@NonNull RuntimeException exception) {
        return ResponseEntity.badRequest().body(new MessageResponse(exception.getMessage()));
    }
}
