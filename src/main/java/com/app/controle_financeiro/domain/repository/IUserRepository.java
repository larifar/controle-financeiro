package com.app.controle_financeiro.domain.repository;

import com.app.controle_financeiro.domain.entities.User;

public interface IUserRepository {
    User save(User user);
    void delete(long id);
    User findById(long id);
}
