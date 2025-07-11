package com.app.controle_financeiro.domain.useCases;

import com.app.controle_financeiro.domain.entities.Transaction;

public interface ICreateTransaction {
    Transaction save(Transaction transaction);
}
