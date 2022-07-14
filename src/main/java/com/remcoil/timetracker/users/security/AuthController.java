package com.remcoil.timetracker.users.security;

import com.remcoil.timetracker.users.user.PublicUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/sign-in")
    @ResponseBody
    public ResponseEntity<Jwt> authenticateUser(@RequestBody UserCredentials credentials) {
        Jwt jwt = authenticationService.authenticateUser(credentials.getId(), credentials.getPassword());
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Jwt> registerUser(@RequestBody UserData user) {
        Jwt token = authenticationService.registerUser(user.toUser());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/users")
    public @ResponseBody List<PublicUser> getAll() {
        return authenticationService.getAll().stream().map(PublicUser::new).collect(Collectors.toList());
    }
}
