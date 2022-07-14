package com.remcoil.timetracker.users.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCredentials {
    private String id;
    private String password;
}
