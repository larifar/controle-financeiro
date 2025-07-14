package com.app.controle_financeiro.infra.controller;

import com.app.controle_financeiro.application.useCases.IFindUserByEmail;
import com.app.controle_financeiro.application.useCases.IFindUserById;
import com.app.controle_financeiro.application.useCases.IFindUserByTelegramId;
import com.app.controle_financeiro.domain.entities.User;
import com.app.controle_financeiro.infra.dto.UserResponseDto;
import com.app.controle_financeiro.infra.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserFindController {
    @Autowired
    private IFindUserById findUserById;

    @Autowired
    private IFindUserByEmail findUserByEmail;

    @Autowired
    private IFindUserByTelegramId findUserByTelegramId;

    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable("id") long userId){
        User user = findUserById.find(userId);
        return ResponseEntity.ok(UserMapper.fromDomaintoResponseDto(user));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> findByEmail(@PathVariable("email") String email){
        User user = findUserByEmail.find(email);
        return ResponseEntity.ok(UserMapper.fromDomaintoResponseDto(user));
    }

    @GetMapping("/telegram/{id}")
    public ResponseEntity<UserResponseDto> findByTelegramId(@PathVariable("id") long telegramId){
        User user = findUserByTelegramId.find(telegramId);
        return ResponseEntity.ok(UserMapper.fromDomaintoResponseDto(user));
    }
}
