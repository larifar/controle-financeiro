package com.app.controle_financeiro.application.useCases;

import com.app.controle_financeiro.domain.entities.Transaction;
import com.app.controle_financeiro.domain.exceptions.TransactionalNotFoundException;

public interface ISaveTransaction {
    void save(Transaction transaction) throws TransactionalNotFoundException;
}
