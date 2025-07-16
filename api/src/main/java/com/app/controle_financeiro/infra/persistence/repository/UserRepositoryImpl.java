package com.app.controle_financeiro.infra.persistence.repository;

import com.app.controle_financeiro.application.repository.IUserRepository;
import com.app.controle_financeiro.domain.entities.User;
import com.app.controle_financeiro.domain.exceptions.ExceptionCodeEnum;
import com.app.controle_financeiro.domain.exceptions.UserNotFoundException;
import com.app.controle_financeiro.infra.mapper.UserMapper;
import com.app.controle_financeiro.infra.persistence.entities.UserEntity;

import java.util.Optional;

public class UserRepositoryImpl implements IUserRepository {

    private final IUserRepositoryJpa repositoryJpa;

    public UserRepositoryImpl(IUserRepositoryJpa repositoryJpa) {
        this.repositoryJpa = repositoryJpa;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = repositoryJpa.save(UserMapper.toEntity(user));
        return UserMapper.toDomain(userEntity);
    }

    @Override
    public void delete(long id) {
        boolean userExists = repositoryJpa.existsById(id);
        if (!userExists){
            throw new UserNotFoundException(ExceptionCodeEnum.USER01.getMessage(), ExceptionCodeEnum.USER01.getCode());
        }
        repositoryJpa.deleteById(id);
    }

    @Override
    public Optional<User> findById(long id) {
        return repositoryJpa.findById(id)
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repositoryJpa.findByEmail(email)
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByTelegramId(long id) {
        return repositoryJpa.findByTelegramId(id)
                .map(UserMapper::toDomain);
    }
}
