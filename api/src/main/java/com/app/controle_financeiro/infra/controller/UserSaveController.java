package com.app.controle_financeiro.infra.controller;

import com.app.controle_financeiro.application.useCases.ICreateUser;
import com.app.controle_financeiro.application.useCases.IDeleteUser;
import com.app.controle_financeiro.application.useCases.ISaveUser;
import com.app.controle_financeiro.domain.entities.User;
import com.app.controle_financeiro.infra.dto.UserRequestDto;
import com.app.controle_financeiro.infra.dto.UserResponseDto;
import com.app.controle_financeiro.infra.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserSaveController {
    @Autowired
    private ICreateUser createUser;
    @Autowired
    private ISaveUser saveUser;

    @Autowired
    private IDeleteUser deleteUser;

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto){
        User user = createUser.save(UserMapper.fromDtoToDomain(userRequestDto));
        return ResponseEntity.ok(UserMapper.fromDomaintoResponseDto(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@RequestBody UserRequestDto userRequestDto, @PathVariable long id){
        User user = UserMapper.fromDtoToDomain(userRequestDto);
        user.setId(id);
        saveUser.save(user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id){
        deleteUser.delete(id);
        return ResponseEntity.noContent().build();
    }
}
