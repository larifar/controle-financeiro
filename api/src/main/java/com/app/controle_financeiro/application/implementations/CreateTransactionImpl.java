package com.app.controle_financeiro.application.implementations;

import com.app.controle_financeiro.domain.entities.Transaction;
import com.app.controle_financeiro.domain.exceptions.ExceptionCodeEnum;
import com.app.controle_financeiro.domain.exceptions.TransactionException;
import com.app.controle_financeiro.domain.exceptions.UserNotFoundException;
import com.app.controle_financeiro.application.repository.ITransactionRepository;
import com.app.controle_financeiro.application.repository.IUserRepository;
import com.app.controle_financeiro.application.useCases.ICreateTransaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreateTransactionImpl implements ICreateTransaction {
    private final ITransactionRepository transactionRepository;
    private final IUserRepository userRepository;

    public CreateTransactionImpl(ITransactionRepository repository, IUserRepository userRepository) {
        this.transactionRepository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        if (transaction.getUserId() <= 0){
            throw new UserNotFoundException(ExceptionCodeEnum.USER01.getMessage(), ExceptionCodeEnum.USER01.getCode());
        }
        if (userRepository.findById(transaction.getUserId()).isEmpty()){
            throw new UserNotFoundException(ExceptionCodeEnum.USER01.getMessage(), ExceptionCodeEnum.USER01.getCode());
        }
        if (transaction.getValue() == null || transaction.getValue().compareTo(BigDecimal.ZERO) <=  0 ){
            throw new TransactionException(ExceptionCodeEnum.TRA002.getCode(), ExceptionCodeEnum.TRA002.getCode());
        }
        if(transaction.getDate() == null){
            transaction.setDate(LocalDateTime.now());
        }
        return transactionRepository.save(transaction);
    }
}
