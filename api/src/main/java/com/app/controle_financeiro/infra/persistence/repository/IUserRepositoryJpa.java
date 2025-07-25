package com.app.controle_financeiro.infra.persistence.repository;

import com.app.controle_financeiro.infra.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepositoryJpa extends JpaRepository<UserEntity, Long>{
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByTelegramId(long telegramId);
}
