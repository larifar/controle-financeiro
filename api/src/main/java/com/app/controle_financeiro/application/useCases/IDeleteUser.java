package com.app.controle_financeiro.application.useCases;

import com.app.controle_financeiro.domain.exceptions.UserNotFoundException;

public interface IDeleteUser {
    void delete(long id) throws UserNotFoundException;
}
