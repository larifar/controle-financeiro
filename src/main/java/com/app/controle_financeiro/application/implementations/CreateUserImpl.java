package com.app.controle_financeiro.application.implementations;

import com.app.controle_financeiro.domain.entities.User;
import com.app.controle_financeiro.domain.exceptions.ExceptionCodeEnum;
import com.app.controle_financeiro.domain.exceptions.UserAlreadyExistsException;
import com.app.controle_financeiro.domain.exceptions.UserException;
import com.app.controle_financeiro.application.repository.IUserRepository;
import com.app.controle_financeiro.application.useCases.ICreateUser;

public class CreateUserImpl implements ICreateUser {
    private final IUserRepository userRepository;

    public CreateUserImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        if (user.getTelegramId() <= 0 ) {
            throw new UserException("Usuário deve ter um telegram Id!", ExceptionCodeEnum.USER03.getCode());
        }

        if (user.getName() == null || user.getName().isBlank()) {
            throw new UserException("Usuário deve ter um nome!", ExceptionCodeEnum.USER03.getCode());
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new UserException("Usuário deve ter uma senha!", ExceptionCodeEnum.USER03.getCode());
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new UserException("Usuário deve ter um email!", ExceptionCodeEnum.USER03.getCode());
        }

        if (userRepository.findByTelegramId(user.getTelegramId()).isPresent()){
            throw new UserAlreadyExistsException("Já existe um usuário cadastrado com essa conta de telegram!", ExceptionCodeEnum.USER02.getCode());
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("Já existe um usuário cadastrado com esse email!", ExceptionCodeEnum.USER02.getCode());
        }
        return userRepository.save(user);
    }
}
