package com.app.controle_financeiro.application.useCases;

import com.app.controle_financeiro.domain.entities.Transaction;

public interface ICreateTransaction {
    Transaction save(Transaction transaction);
}
