package com.remcoil.timetracker.users.core;

import lombok.AllArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor
@Entity
@Table(name = "users", schema = "time_tracker")
public class UserEntity {
    @Id
    @GeneratedValue
    @Type(type = "org.hibernate.type.PostgresUUIDType")
    UUID id;
    String name;
    String surname;
    String password;
    @Enumerated(EnumType.STRING)
    UserRole role;

    protected UserEntity() {
    }

    public UserEntity(User user) {
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

