package com.app.controle_financeiro.application.useCases;

import com.app.controle_financeiro.domain.entities.User;
import com.app.controle_financeiro.domain.exceptions.UserNotFoundException;

public interface ISaveUser {
    void save(User user) throws UserNotFoundException;
}
