package com.remcoil.timetracker.users.admin;

import com.remcoil.timetracker.users.core.User;
import com.remcoil.timetracker.users.core.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class FullUser {
    private UUID id;
    private String name;
    private String surname;
    private String password;
    private UserRole role;

    private FullUser(UUID id, String name, String surname, String password, UserRole role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.role = role;
    }

    public static FullUser build(User user) {
        return new FullUser(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getPassword(),
                user.getRole()
        );
    }

    public User toUser() {
        return new User(id, name, surname, password, role);
    }
}
