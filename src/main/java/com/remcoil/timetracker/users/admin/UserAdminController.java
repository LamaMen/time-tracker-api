package com.remcoil.timetracker.users.admin;

import com.remcoil.timetracker.core.MessageResponse;
import com.remcoil.timetracker.users.core.User;
import com.remcoil.timetracker.users.core.UserService;
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
        return users.stream().map(FullUser::build).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public @ResponseBody FullUser getById(@PathVariable UUID id) {
        return FullUser.build(userService.getById(id));
    }

    @PostMapping
    public @ResponseBody FullUser add(@RequestBody FullUser user) {
        User newUser = userService.save(user.toUser());
        return FullUser.build(newUser);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody FullUser user, @AuthenticationPrincipal User current) {
        if (id.equals(current.getId()) && user.getRole() != current.getRole()) {
            return ResponseEntity.badRequest().body(new MessageResponse("You are trying to change yourself role"));
        }

        User newUser = userService.save(user.toUser(id));
        return ResponseEntity.ok(FullUser.build(newUser));
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
