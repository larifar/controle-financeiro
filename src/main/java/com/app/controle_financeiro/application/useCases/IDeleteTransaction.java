package com.app.controle_financeiro.application.useCases;

import com.app.controle_financeiro.domain.exceptions.TransactionalNotFoundException;

public interface IDeleteTransaction {
    void delete(long id) throws TransactionalNotFoundException;
}
