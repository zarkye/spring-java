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
            Object avatarValue = avatar.containsKey("avatarUrl")
                    ? avatar.get("avatarUrl")
                    : avatar.get("avatar");
            if (avatarValue instanceof String avatarUrl) {
                existingUser.setAvatar(avatarUrl);
            }
            return existingUser;
        });
    }

    public Optional<UserCreationDTO> update(UUID id, UserCreationDTO updateDate) {
        Optional<UserCreationDTO> userOptional = findById(id);

        if (userOptional.isPresent()) {
            UserCreationDTO existingUser = userOptional.get();
            existingUser.setName(updateDate.getName());
            existingUser.setEmail(updateDate.getEmail());
            return Optional.of(existingUser);
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
