package com.app.controle_financeiro.application.useCases;

import com.app.controle_financeiro.domain.entities.Transaction;
import com.app.controle_financeiro.domain.entities.TransactionTypeEnum;

import java.time.LocalDateTime;
import java.util.List;

public interface IGetTransactionsByTypeAndByPeriod {
    List<Transaction> getTransactions(LocalDateTime from, LocalDateTime until, long userId, TransactionTypeEnum type);
}
