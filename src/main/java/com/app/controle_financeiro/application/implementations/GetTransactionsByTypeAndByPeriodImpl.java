package com.app.controle_financeiro.application.implementations;

import com.app.controle_financeiro.application.repository.ITransactionRepository;
import com.app.controle_financeiro.application.repository.IUserRepository;
import com.app.controle_financeiro.application.useCases.IGetTransactionsByTypeAndByPeriod;
import com.app.controle_financeiro.domain.entities.Transaction;
import com.app.controle_financeiro.domain.entities.TransactionTypeEnum;
import com.app.controle_financeiro.domain.exceptions.DateTimeException;
import com.app.controle_financeiro.domain.exceptions.ExceptionCodeEnum;
import com.app.controle_financeiro.domain.exceptions.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public class GetTransactionsByTypeAndByPeriodImpl implements IGetTransactionsByTypeAndByPeriod {
    private final ITransactionRepository transactionRepository;
    private final IUserRepository userRepository;

    public GetTransactionsByTypeAndByPeriodImpl(ITransactionRepository transactionRepository, IUserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Transaction> getTransactions(LocalDateTime from, LocalDateTime until, long userId, TransactionTypeEnum type) {
        if (userRepository.findById(userId).isEmpty()){
            throw new UserNotFoundException(ExceptionCodeEnum.USER01.getMessage(), ExceptionCodeEnum.USER01.getCode());
        }
        if (from == null || until == null){
            throw new DateTimeException(ExceptionCodeEnum.DATE02.getMessage(), ExceptionCodeEnum.DATE02.getCode());
        }
        if (from.isAfter(until)){
            throw new DateTimeException(ExceptionCodeEnum.DATE01.getMessage(), ExceptionCodeEnum.DATE01.getCode());
        }
        return transactionRepository.getFromDateUntilDatebyType(from, until, userId, type);
    }
}
