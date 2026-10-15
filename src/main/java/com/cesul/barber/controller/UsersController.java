package com.cesul.barber.controller;


import com.cesul.barber.dtos.UserCreationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuários", description = "Gerenciamento de usuarios da barbearia")
public class UsersController {

    private final List<UserCreationDTO> users = new ArrayList<>();

    @GetMapping
    @Operation(summary = "Listar Todos", description = "Retorna a lista de todos os usuários")
    public ResponseEntity<List<UserCreationDTO>> getUsers() {
        return ResponseEntity.ok(users);
    }

    @PostMapping
    @Operation(summary = "Criação de usuário", description = "Realiza a criação de um usuário")
    public ResponseEntity<UserCreationDTO> create(@RequestBody UserCreationDTO user) {
        user.setId(UUID.randomUUID());
        this.users.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um usuário", description = "Realiza a remoção de um usuário")
    public ResponseEntity<Void> delete(@PathVariable UUID id, @RequestBody UserCreationDTO user) {

        boolean removed = this.users.removeIf(u -> u.getId().equals(id));

        if(removed) return ResponseEntity.noContent().build();

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/clear")
    @Operation(summary = "Remove todos os usuários")
    public ResponseEntity<Void> clearUsers() {
        this.users.clear();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserCreationDTO> put(@PathVariable UUID id, @RequestBody UserCreationDTO user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                user.setId(id);
                this.users.set(i, user);
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/avatar")
    public ResponseEntity<UserCreationDTO> updateAvatar(@PathVariable UUID id, @RequestBody Map<String, Object> avatar) {

        Optional<UserCreationDTO> userOptional = users.stream().filter(u -> u.getId().equals(id)).findFirst();
        if(userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserCreationDTO existingUser = userOptional.get();

        if(avatar.containsKey("avatarUrl")) {
            existingUser.setAvatar((String) avatar.get("avatarUrl"));
        }

//        for(UserCreationDTO u : users) {
//            if(u.getId().equals(id)) {
//                user.setId(id);
//                u.setAvatar(user.getAvatar());
//                return ResponseEntity.ok(user);
//            }
//        }
        return ResponseEntity.ok(existingUser);
    }
}
