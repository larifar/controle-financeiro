package com.app.controle_financeiro.application.implementations;

import com.app.controle_financeiro.application.repository.ITransactionRepository;
import com.app.controle_financeiro.domain.exceptions.ExceptionCodeEnum;
import com.app.controle_financeiro.domain.exceptions.TransactionNotFoundException;
import com.app.controle_financeiro.application.useCases.IDeleteTransaction;

public class DeleteTransactionImpl implements IDeleteTransaction {

    private final ITransactionRepository transactionRepository;

    public DeleteTransactionImpl(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void delete(long id) throws TransactionNotFoundException {
        if (transactionRepository.findById(id).isEmpty()){
            throw new TransactionNotFoundException(ExceptionCodeEnum.TRA001.getMessage(), ExceptionCodeEnum.TRA001.getCode());
        }
        transactionRepository.delete(id);
    }
}
