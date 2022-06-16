package com.remcoil.timetracker.users.core;

import lombok.AllArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor
@Entity
@Table(name = "users", schema = "time_tracker")
public class UserTable {
    @Id
    @GeneratedValue
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    UUID id;
    String name;
    String surname;
    String password;
    @Enumerated(EnumType.STRING)
    UserRole role;

    protected UserTable() {
    }
}

