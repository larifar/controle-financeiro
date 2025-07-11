package com.app.controle_financeiro.domain.useCases.implementations;

import com.app.controle_financeiro.domain.entities.Transaction;
import com.app.controle_financeiro.domain.exceptions.ExceptionCodeEnum;
import com.app.controle_financeiro.domain.exceptions.TransactionalException;
import com.app.controle_financeiro.domain.exceptions.UserNotFoundException;
import com.app.controle_financeiro.domain.repository.ITransactionRepository;
import com.app.controle_financeiro.domain.repository.IUserRepository;
import com.app.controle_financeiro.domain.useCases.ICreateTransaction;

import java.math.BigDecimal;

public class CreateTransactionImpl implements ICreateTransaction {
    private final ITransactionRepository transactionRepository;
    private final IUserRepository userRepository;

    public CreateTransactionImpl(ITransactionRepository repository, IUserRepository userRepository) {
        this.transactionRepository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        if (userRepository.findById(transaction.getUserId()) == null){
            throw new UserNotFoundException(ExceptionCodeEnum.USER01.getMessage(), ExceptionCodeEnum.USER01.getCode());
        }
        if (transaction.getValue() == null || transaction.getValue().compareTo(BigDecimal.ZERO) <=  0 ){
            throw new TransactionalException(ExceptionCodeEnum.TRA002.getCode(), ExceptionCodeEnum.TRA002.getCode());
        }
        return transactionRepository.save(transaction);
    }
}
