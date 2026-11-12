package com.cesul.barber.controller;


import com.cesul.barber.dtos.UserCreationDTO;
import com.cesul.barber.services.UserService;
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

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Listar Todos", description = "Retorna a lista de todos os usuários")
    public ResponseEntity<List<UserCreationDTO>> getUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @PostMapping
    @Operation(summary = "Criação de usuário", description = "Realiza a criação de um usuário")
    public ResponseEntity<UserCreationDTO> create(@RequestBody UserCreationDTO user) {
        UserCreationDTO newUser = this.userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um usuário", description = "Realiza a remoção de um usuário")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (this.userService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/clear")
    @Operation(summary = "Remove todos os usuários")
    public ResponseEntity<Void> clearUsers() {
        this.userService.clear();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Substituição do conteúdo do usuário", description = "Substituição completa de todo o conteúdo do usuário por um novo")
    public ResponseEntity<UserCreationDTO> put(@PathVariable UUID id, @RequestBody UserCreationDTO user) {
        return this.userService.update(id, user).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/avatar")
    @Operation(summary = "Atualização de avatar", description = "Atualiza o avatar do usuário específico com o url da imagem nova")
    public ResponseEntity<UserCreationDTO> updateAvatar(@PathVariable UUID id, @RequestBody Map<String, Object> avatar) {
        return this.userService.updateAvatar(id, avatar).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
