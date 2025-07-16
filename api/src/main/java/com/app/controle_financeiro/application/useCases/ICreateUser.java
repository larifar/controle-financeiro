package com.app.controle_financeiro.application.useCases;

import com.app.controle_financeiro.domain.entities.User;

public interface ICreateUser {
    User save(User user);
}
