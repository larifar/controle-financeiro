package com.app.controle_financeiro.domain.useCases;

import com.app.controle_financeiro.domain.entities.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface IGetTransactionsByPeriod {
    List<Transaction> getTransactions(LocalDateTime from, LocalDateTime until, long userId);
}
