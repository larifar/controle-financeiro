package com.app.controle_financeiro.application.implementations;

import com.app.controle_financeiro.application.repository.IUserRepository;
import com.app.controle_financeiro.application.useCases.IFindUserByTelegramId;
import com.app.controle_financeiro.domain.entities.User;
import com.app.controle_financeiro.domain.exceptions.ExceptionCodeEnum;
import com.app.controle_financeiro.domain.exceptions.UserNotFoundException;

import java.util.Optional;

public class FindUserByTelegramIdImpl implements IFindUserByTelegramId {
    private final IUserRepository userRepository;

    public FindUserByTelegramIdImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User find(long telegramId) throws UserNotFoundException {
        Optional<User> user = userRepository.findByTelegramId(telegramId);
        if (user.isEmpty()){
            throw new UserNotFoundException(ExceptionCodeEnum.USER01.getMessage(), ExceptionCodeEnum.USER01.getCode());
        }
        return user.get();
    }
}
