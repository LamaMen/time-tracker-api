package com.remcoil.timetracker.users.admin;

import com.remcoil.timetracker.users.core.User;
import com.remcoil.timetracker.users.core.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor

public class FullUser {
    private UUID id;
    private String name;
    private String surname;
    private String password;
    private UserRole role;

    public FullUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    public User toUser() {
        return new User(id, name, surname, password, role);
    }
}
