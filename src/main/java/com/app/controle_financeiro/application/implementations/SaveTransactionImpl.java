package com.app.controle_financeiro.application.implementations;

import com.app.controle_financeiro.application.repository.ITransactionRepository;
import com.app.controle_financeiro.application.repository.IUserRepository;
import com.app.controle_financeiro.application.useCases.ISaveTransaction;
import com.app.controle_financeiro.domain.entities.Transaction;
import com.app.controle_financeiro.domain.exceptions.ExceptionCodeEnum;
import com.app.controle_financeiro.domain.exceptions.TransactionalException;
import com.app.controle_financeiro.domain.exceptions.TransactionalNotFoundException;
import com.app.controle_financeiro.domain.exceptions.UserNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SaveTransactionImpl implements ISaveTransaction {
    private final ITransactionRepository transactionRepository;
    private final IUserRepository userRepository;

    public SaveTransactionImpl(ITransactionRepository transactionRepository, IUserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void save(Transaction transaction) throws TransactionalNotFoundException {
        if (transaction.getId() > 0){
            if (transactionRepository.findById(transaction.getId()).isEmpty()){
                throw new TransactionalNotFoundException(ExceptionCodeEnum.TRA001.getMessage(), ExceptionCodeEnum.TRA001.getCode());
            }
        }
        if (transaction.getUserId() <= 0){
            throw new TransactionalException(ExceptionCodeEnum.TRA003.getMessage(), ExceptionCodeEnum.TRA003.getCode());
        }
        if (userRepository.findById(transaction.getUserId()).isEmpty()){
            throw new UserNotFoundException(ExceptionCodeEnum.USER01.getMessage(), ExceptionCodeEnum.USER01.getCode());
        }
        if (transaction.getValue() == null || transaction.getValue().compareTo(BigDecimal.ZERO) <=  0 ){
            throw new TransactionalException(ExceptionCodeEnum.TRA002.getCode(), ExceptionCodeEnum.TRA002.getCode());
        }
        if(transaction.getDate() == null){
            transaction.setDate(LocalDateTime.now());
        }
        transactionRepository.save(transaction);
    }
}
