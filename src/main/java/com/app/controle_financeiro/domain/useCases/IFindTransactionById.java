package com.app.controle_financeiro.domain.useCases;

import com.app.controle_financeiro.domain.entities.Transaction;
import com.app.controle_financeiro.domain.exceptions.TransactionalNotFoundException;

public interface IFindTransactionById {
    Transaction find(long id) throws TransactionalNotFoundException;
}
