package com.app.controle_financeiro.infra.persistence.repository;

import com.app.controle_financeiro.domain.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionRepositoryJpa extends JpaRepository<Transaction, Long> {
}
