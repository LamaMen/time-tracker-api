package com.remcoil.timetracker.users.core.data;

import com.remcoil.timetracker.users.core.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "time_tracker")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public UserEntity(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    public UserEntity(UUID id) {
        this.id = id;
    }

    public User toUser() {
        return new User(id, name, surname, password, role);
    }
}

