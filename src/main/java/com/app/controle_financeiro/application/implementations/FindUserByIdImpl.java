package com.app.controle_financeiro.application.implementations;

import com.app.controle_financeiro.application.repository.IUserRepository;
import com.app.controle_financeiro.application.useCases.IFindUserById;
import com.app.controle_financeiro.domain.entities.User;
import com.app.controle_financeiro.domain.exceptions.ExceptionCodeEnum;
import com.app.controle_financeiro.domain.exceptions.UserNotFoundException;

import java.util.Optional;

public class FindUserByIdImpl implements IFindUserById {
    private final IUserRepository userRepository;

    public FindUserByIdImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User find(long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new UserNotFoundException(ExceptionCodeEnum.USER01.getMessage(), ExceptionCodeEnum.USER01.getCode());
        }
        return user.get();
    }
}
