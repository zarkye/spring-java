package com.cesul.barber.services;

import com.cesul.barber.dtos.UserCreationDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final List<UserCreationDTO> users = new ArrayList<>();

    public UserCreationDTO create(UserCreationDTO user) {
        user.setId(UUID.randomUUID());
        this.users.add(user);
        return user;
    }

    public List<UserCreationDTO> getAllUsers() {
        return this.users;
    }

    public Optional<UserCreationDTO> findById(UUID id) {
        return this.users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public Optional<UserCreationDTO> updateAvatar(UUID id, Map<String, Object> avatar) {
        return findById(id).map(existingUser -> {
            if (avatar.containsKey("avatarUrl")) {
                existingUser.setAvatar((String) avatar.get("avatarUrl"));
            }
            return existingUser;
        });
    }

    public Optional<UserCreationDTO> update(UUID id, UserCreationDTO user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                user.setId(id);
                users.set(i, user);
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public boolean delete(UUID id) {
        return users.removeIf(user -> user.getId().equals(id));
    }

    public void clear() {
        users.clear();
    }
}
