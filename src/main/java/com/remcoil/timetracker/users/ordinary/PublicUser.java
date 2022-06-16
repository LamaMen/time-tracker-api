package com.remcoil.timetracker.users.ordinary;

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

    private PublicUser(UUID id, String name, String surname, UserRole role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.role = role;
    }

    public static @NonNull PublicUser build(@NonNull User user) {
        return new PublicUser(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getRole()
        );
    }
}
