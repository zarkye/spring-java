package com.cesul.barber.services;

import com.cesul.barber.dtos.AvatarUpdateDTO;
import com.cesul.barber.dtos.UserCreationDTO;
import com.cesul.barber.entities.UserEntity;
import com.cesul.barber.mappers.UserMapper;
import com.cesul.barber.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserCreationDTO create(UserCreationDTO userDTO) {
        UserEntity user = userMapper.toEntity(userDTO);
        return userMapper.toDTO(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public List<UserCreationDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public Optional<UserCreationDTO> findById(UUID id) {
        return userRepository.findById(id).map(userMapper::toDTO);
    }

    public Optional<UserCreationDTO> updateAvatar(UUID id, AvatarUpdateDTO avatarUpdate) {
        return userRepository.findById(id).map(user -> {
            user.setAvatar(avatarUpdate.getAvatarUrl());
            return userMapper.toDTO(userRepository.save(user));
        });
    }

    public Optional<UserCreationDTO> update(UUID id, UserCreationDTO updateData) {
        return userRepository.findById(id).map(user -> {
            user.setName(updateData.getName());
            user.setEmail(updateData.getEmail());
            user.setAvatar(updateData.getAvatar());
            return userMapper.toDTO(userRepository.save(user));
        });
    }

    public boolean delete(UUID id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    return true;
                })
                .orElse(false);
    }

    public void clear() {
        userRepository.deleteAll();
    }
}
