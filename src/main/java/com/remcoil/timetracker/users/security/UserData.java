package com.remcoil.timetracker.users.security;

import com.remcoil.timetracker.users.core.User;
import com.remcoil.timetracker.users.core.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserData {
    private String name;
    private String surname;
    private String password;

    public User toUser() {
        return new User(null, name, surname, password, UserRole.Employee);
    }
}
