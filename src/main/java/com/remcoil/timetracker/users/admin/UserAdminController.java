package com.remcoil.timetracker.users.admin;

import com.remcoil.timetracker.core.MessageResponse;
import com.remcoil.timetracker.users.core.domain.User;
import com.remcoil.timetracker.users.core.domain.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/api/v1/admin/users")
@SecurityRequirement(name = "time-tracker")
public class UserAdminController {
    private final UserService userService;

    public UserAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public @ResponseBody List<FullUser> getAll() {
        List<User> users = userService.getAll();
        return users.stream().map(FullUser::new).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public @ResponseBody FullUser getById(@PathVariable UUID id) {
        User user = userService.getById(id);
        return new FullUser(user);
    }

    @PostMapping
    public @ResponseBody FullUser add(@RequestBody FullUser user) {
        User newUser = userService.save(user.toUser());
        return new FullUser(newUser);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody FullUser user, @AuthenticationPrincipal User current) {
        if (user.getId().equals(current.getId()) && user.getRole() != current.getRole()) {
            return ResponseEntity.badRequest().body(new MessageResponse("You are trying to change yourself role"));
        }

        User newUser = userService.save(user.toUser());
        return ResponseEntity.ok(new FullUser(newUser));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        if (id.equals(user.getId())) {
            return ResponseEntity.badRequest().body(new MessageResponse("You are trying to remove yourself"));
        }

        userService.delete(id);
        return ResponseEntity.ok(new MessageResponse("User deleted"));
    }
}
