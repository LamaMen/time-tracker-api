package com.remcoil.timetracker.projects.core;


import com.remcoil.timetracker.core.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProjectExceptionHandler {
    @ExceptionHandler(value = ProjectNotFoundException.class)
    public ResponseEntity<MessageResponse> projectNotFound(@NonNull ProjectNotFoundException exception) {
        return ResponseEntity.badRequest().body(new MessageResponse(exception.getMessage()));
    }

    @ExceptionHandler(value = ProjectAlreadyArchivedException.class)
    public ResponseEntity<MessageResponse> projectAlreadyArchived(@NonNull ProjectAlreadyArchivedException exception) {
        return ResponseEntity.badRequest().body(new MessageResponse(exception.getMessage()));
    }
}
