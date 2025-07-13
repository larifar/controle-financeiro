package com.app.controle_financeiro.application.implementations;

import com.app.controle_financeiro.application.repository.IUserRepository;
import com.app.controle_financeiro.application.useCases.ISaveUser;
import com.app.controle_financeiro.domain.entities.User;
import com.app.controle_financeiro.domain.exceptions.ExceptionCodeEnum;
import com.app.controle_financeiro.domain.exceptions.UserException;
import com.app.controle_financeiro.domain.exceptions.UserNotFoundException;

public class SaveUserImpl implements ISaveUser {
    private final IUserRepository userRepository;

    public SaveUserImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) throws UserNotFoundException {
        if (user.getId() <= 0){
            throw new UserException("Usuário deve ter id!", ExceptionCodeEnum.USER04.getCode());
        }
        if (userRepository.findById(user.getId()).isEmpty()){
            throw new UserNotFoundException(ExceptionCodeEnum.USER01.getMessage(), ExceptionCodeEnum.USER01.getCode());
        }
        if (user.getTelegramId() <= 0 ) {
            throw new UserException("Usuário deve ter um telegram Id", ExceptionCodeEnum.USER03.getCode());
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new UserException("Usuário deve ter um email", ExceptionCodeEnum.USER03.getCode());
        }
        if (user.getName() == null || user.getName().isBlank()) {
            throw new UserException("Usuário deve ter um nome!", ExceptionCodeEnum.USER03.getCode());
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new UserException("Usuário deve ter uma senha!", ExceptionCodeEnum.USER03.getCode());
        }
        userRepository.save(user);
    }
}
