package com.remcoil.timetracker.sessions.user;


import com.remcoil.timetracker.core.MessageResponse;
import com.remcoil.timetracker.sessions.core.SessionService;
import com.remcoil.timetracker.users.core.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/v1/user/sessions")
@SecurityRequirement(name = "time-tracker")
public class SessionUserController {
    private final SessionService sessionService;

    public SessionUserController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/start/{projectId}")
    public ResponseEntity<MessageResponse> start(@PathVariable int projectId, @AuthenticationPrincipal User user) {
        sessionService.startWork(projectId, user);

        return ResponseEntity.ok(new MessageResponse("Tracking started"));
    }

    @GetMapping("/stop/{projectId}")
    public ResponseEntity<MessageResponse> stop(@PathVariable int projectId, @AuthenticationPrincipal User user) {
        sessionService.stopWork(projectId, user);

        return ResponseEntity.ok(new MessageResponse("Tracking stopped"));
    }
}
