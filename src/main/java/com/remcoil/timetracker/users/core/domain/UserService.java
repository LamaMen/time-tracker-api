package com.remcoil.timetracker.users.core.domain;

import com.remcoil.timetracker.users.core.data.UserEntity;
import com.remcoil.timetracker.users.core.data.UserRepository;
import com.remcoil.timetracker.users.core.exceptions.UserNotFoundException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(@NonNull UUID id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return userEntity.toUser();
    }

    public List<User> getAll() {
        return userRepository.findAll().stream().map(UserEntity::toUser).collect(Collectors.toList());
    }

    public User save(@NonNull User user) {
        UserEntity userEntity = userRepository.save(new UserEntity(user));
        return userEntity.toUser();
    }

    public void delete(@NonNull UUID id) {
        if (!userRepository.existsById(id)) throw new UserNotFoundException(id);

        userRepository.deleteById(id);
    }
}
