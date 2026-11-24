package com.cesul.barber.controller;


import com.cesul.barber.dtos.AvatarUpdateDTO;
import com.cesul.barber.dtos.UserCreationDTO;
import com.cesul.barber.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{id}")
    @Operation(summary = "Buscar usuário", description = "Retorna um usuário pelo identificador")
    public ResponseEntity<UserCreationDTO> getUser(@PathVariable UUID id) {
        return this.userService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criação de usuário", description = "Realiza a criação de um usuário")
    public ResponseEntity<UserCreationDTO> create(@Valid @RequestBody UserCreationDTO user) {
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
    public ResponseEntity<UserCreationDTO> put(@PathVariable UUID id, @Valid @RequestBody UserCreationDTO user) {
        return this.userService.update(id, user).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/avatar")
    @Operation(summary = "Atualização de avatar", description = "Atualiza o avatar do usuário específico com a URL da nova imagem")
    public ResponseEntity<UserCreationDTO> updateAvatar(
            @PathVariable UUID id,
            @Valid @RequestBody AvatarUpdateDTO avatar
    ) {
        return this.userService.updateAvatar(id, avatar)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
