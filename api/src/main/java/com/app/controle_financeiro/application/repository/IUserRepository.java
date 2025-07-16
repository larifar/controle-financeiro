package com.app.controle_financeiro.application.repository;

import com.app.controle_financeiro.domain.entities.User;

import java.util.Optional;

public interface IUserRepository {
    User save(User user);
    void delete(long id);
    Optional<User> findById(long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByTelegramId(long id);
}
