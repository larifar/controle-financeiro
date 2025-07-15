package com.app.controle_financeiro.application.useCases;

import com.app.controle_financeiro.domain.exceptions.UserNotFoundException;

public interface IDeleteAllTransactions {
    void deleteAllByUserId(long id) throws UserNotFoundException;
}
