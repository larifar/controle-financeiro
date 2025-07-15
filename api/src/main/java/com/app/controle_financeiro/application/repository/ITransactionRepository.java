package com.app.controle_financeiro.application.repository;

import com.app.controle_financeiro.domain.entities.Transaction;
import com.app.controle_financeiro.domain.entities.TransactionTypeEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ITransactionRepository {
    Transaction save(Transaction transaction);
    void deleteAll(long userId);
    void delete(long id);
    Optional<Transaction> findById(long id);
    List<Transaction> getFromDateUntilDatebyType(LocalDateTime from, LocalDateTime until, long userId, TransactionTypeEnum type);
}
