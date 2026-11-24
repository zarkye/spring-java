package com.cesul.barber.mappers;

import com.cesul.barber.dtos.UserCreationDTO;
import com.cesul.barber.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(UserCreationDTO dto) {
        UserEntity user = new UserEntity();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAvatar(dto.getAvatar());
        return user;
    }

    public UserCreationDTO toDTO(UserEntity user) {
        UserCreationDTO dto = new UserCreationDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setAvatar(user.getAvatar());
        return dto;
    }
}
