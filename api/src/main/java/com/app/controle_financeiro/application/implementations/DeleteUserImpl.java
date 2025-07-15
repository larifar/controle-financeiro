package com.app.controle_financeiro.application.implementations;

import com.app.controle_financeiro.application.repository.ITransactionRepository;
import com.app.controle_financeiro.application.repository.IUserRepository;
import com.app.controle_financeiro.application.useCases.IDeleteUser;
import com.app.controle_financeiro.domain.exceptions.ExceptionCodeEnum;
import com.app.controle_financeiro.domain.exceptions.UserNotFoundException;

public class DeleteUserImpl implements IDeleteUser {
    private final IUserRepository userRepository;
    private final ITransactionRepository transactionRepository;

    public DeleteUserImpl(IUserRepository userRepository, ITransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void delete(long id) throws UserNotFoundException {
        if (userRepository.findById(id).isEmpty()){
            throw new UserNotFoundException(ExceptionCodeEnum.USER01.getMessage(), ExceptionCodeEnum.USER01.getCode());
        }
        transactionRepository.deleteAll(id);
        userRepository.delete(id);
    }
}
