package com.app.controle_financeiro.infra.persistence.repository;

import com.app.controle_financeiro.domain.entities.TransactionTypeEnum;
import com.app.controle_financeiro.infra.persistence.entities.TransactionEntity;
import com.app.controle_financeiro.infra.persistence.entities.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ITransactionRepositoryJpa extends JpaRepository<TransactionEntity, Long> {
    @Query("SELECT t FROM TransactionEntity t WHERE t.userId.id = :userId AND t.type = :type AND t.date BETWEEN :from AND :until")
    List<TransactionEntity> getFromDateUntilDatebyType(
            @Param("from") LocalDateTime from,
            @Param("until") LocalDateTime until,
            @Param("userId") long userId,
            @Param("type") TransactionTypeEnum type
    );

    @Transactional
    void deleteAllByUserId(UserEntity user);
}
