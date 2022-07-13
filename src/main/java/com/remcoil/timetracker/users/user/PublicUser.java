package com.remcoil.timetracker.users.user;

import com.remcoil.timetracker.users.core.User;
import com.remcoil.timetracker.users.core.UserRole;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.UUID;

@Getter
public class PublicUser {
    private final UUID id;
    private final String name;
    private final String surname;
    private final UserRole role;

    public PublicUser(@NonNull User user) {
        id = user.getId();
        name = user.getName();
        surname = user.getSurname();
        role = user.getRole();
    }
}
