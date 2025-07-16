package com.app.controle_financeiro.application.implementations;

import com.app.controle_financeiro.application.repository.IUserRepository;
import com.app.controle_financeiro.application.useCases.IFindUserByEmail;
import com.app.controle_financeiro.domain.entities.User;
import com.app.controle_financeiro.domain.exceptions.ExceptionCodeEnum;
import com.app.controle_financeiro.domain.exceptions.UserNotFoundException;

import java.util.Optional;

public class FindUserByEmailImpl implements IFindUserByEmail {
    private final IUserRepository userRepository;

    public FindUserByEmailImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User find(String email) throws UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()){
            throw new UserNotFoundException(ExceptionCodeEnum.USER01.getMessage(), ExceptionCodeEnum.USER01.getCode());
        }
        return user.get();
    }
}
