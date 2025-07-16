package com.app.controle_financeiro.application.useCases;

import com.app.controle_financeiro.domain.entities.Transaction;
import com.app.controle_financeiro.domain.exceptions.TransactionNotFoundException;

public interface IFindTransactionById {
    Transaction find(long id) throws TransactionNotFoundException;
}
