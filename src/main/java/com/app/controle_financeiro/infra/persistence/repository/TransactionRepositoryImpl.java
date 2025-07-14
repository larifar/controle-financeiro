package com.app.controle_financeiro.infra.persistence.repository;

import com.app.controle_financeiro.application.repository.ITransactionRepository;
import com.app.controle_financeiro.domain.entities.Transaction;
import com.app.controle_financeiro.domain.entities.TransactionTypeEnum;
import com.app.controle_financeiro.domain.exceptions.ExceptionCodeEnum;
import com.app.controle_financeiro.domain.exceptions.TransactionNotFoundException;
import com.app.controle_financeiro.domain.exceptions.UserNotFoundException;
import com.app.controle_financeiro.infra.mapper.TransactionMapper;
import com.app.controle_financeiro.infra.persistence.entities.TransactionEntity;
import com.app.controle_financeiro.infra.persistence.entities.UserEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TransactionRepositoryImpl implements ITransactionRepository {

    private final ITransactionRepositoryJpa transactionRepositoryJpa;
    private final IUserRepositoryJpa userRepositoryJpa;

    public TransactionRepositoryImpl(ITransactionRepositoryJpa transactionRepositoryJpa, IUserRepositoryJpa userRepositoryJpa) {
        this.transactionRepositoryJpa = transactionRepositoryJpa;
        this.userRepositoryJpa = userRepositoryJpa;
    }

    @Override
    public Transaction save(Transaction transaction) {
        UserEntity user = userRepositoryJpa.findById(transaction.getUserId())
                .orElseThrow(()-> new UserNotFoundException(ExceptionCodeEnum.USER01.getMessage(), ExceptionCodeEnum.USER01.getCode()));
        TransactionEntity entity = TransactionMapper.toEntity(transaction, user);
        return TransactionMapper.toDomain(transactionRepositoryJpa.save(entity));
    }

    @Override
    public void delete(long id) {
        boolean transactionExists = transactionRepositoryJpa.existsById(id);
        if (!transactionExists){
            throw new TransactionNotFoundException(ExceptionCodeEnum.TRA001.getMessage(), ExceptionCodeEnum.TRA001.getCode());
        }
        transactionRepositoryJpa.deleteById(id);
    }

    @Override
    public Optional<Transaction> findById(long id) {
        return transactionRepositoryJpa.findById(id)
                .map(TransactionMapper::toDomain);
    }

    @Override
    public List<Transaction> getFromDateUntilDatebyType(LocalDateTime from, LocalDateTime until, long userId, TransactionTypeEnum type) {
        List<TransactionEntity> transactionEntities = transactionRepositoryJpa.getFromDateUntilDatebyType(from, until, userId, type);
        return transactionEntities.stream().map(TransactionMapper::toDomain).toList();
    }
}
