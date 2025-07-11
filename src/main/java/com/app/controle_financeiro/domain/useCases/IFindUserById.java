package com.app.controle_financeiro.domain.useCases;

import com.app.controle_financeiro.domain.entities.User;
import com.app.controle_financeiro.domain.exceptions.UserNotFoundException;

public interface IFindUserById {
    User find(long id) throws UserNotFoundException;
}
