package com.app.controle_financeiro.domain.repository;

import com.app.controle_financeiro.domain.entities.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface ITransactionRepository {
    Transaction save(Transaction transaction);
    void delete(long id);
    Transaction findById(long id);
    List<Transaction> getFromDateUntilDate(LocalDateTime from, LocalDateTime until, long userId);
}
