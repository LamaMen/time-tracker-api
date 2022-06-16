package com.remcoil.timetracker.users.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserCredentials {
    private String id;
    private String password;
}
