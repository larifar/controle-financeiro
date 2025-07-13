package com.app.controle_financeiro.application.implementations;

import com.app.controle_financeiro.application.repository.ITransactionRepository;
import com.app.controle_financeiro.application.useCases.IFindTransactionById;
import com.app.controle_financeiro.domain.entities.Transaction;
import com.app.controle_financeiro.domain.exceptions.ExceptionCodeEnum;
import com.app.controle_financeiro.domain.exceptions.TransactionalNotFoundException;

import java.util.Optional;

public class FindTransactionByIdImpl implements IFindTransactionById {
    private final ITransactionRepository transactionRepository;

    public FindTransactionByIdImpl(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction find(long id) throws TransactionalNotFoundException {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        if (transaction.isEmpty()){
            throw new TransactionalNotFoundException(ExceptionCodeEnum.TRA001.getMessage(), ExceptionCodeEnum.TRA001.getCode());
        }
        return transaction.get();
    }
}
