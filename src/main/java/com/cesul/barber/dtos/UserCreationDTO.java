package com.cesul.barber.dtos;

import java.util.UUID;

public class UserCreationDTO {

    private UUID id;
    private String name;
    private String email;
    private String avatar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public UUID getId() {return id;}
    public void setId(UUID id) {this.id = id;}

    public void setAvatar(String avatar) { this.avatar = avatar;}

    public String getAvatar() {
        return avatar;
    }
}
