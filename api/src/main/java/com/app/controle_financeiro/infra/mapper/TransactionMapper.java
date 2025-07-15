package com.app.controle_financeiro.infra.mapper;

import com.app.controle_financeiro.domain.entities.Transaction;
import com.app.controle_financeiro.infra.dto.TransactionRequestDto;
import com.app.controle_financeiro.infra.dto.TransactionResponseDto;
import com.app.controle_financeiro.infra.persistence.entities.TransactionEntity;
import com.app.controle_financeiro.infra.persistence.entities.UserEntity;

public class TransactionMapper {
    public static Transaction toDomain(TransactionEntity entity){
        Transaction transaction = new Transaction();
        transaction.setId(entity.getId());
        transaction.setDate(entity.getDate());
        transaction.setUserId(entity.getUserId().getId());
        transaction.setType(entity.getType());
        transaction.setValue(entity.getValue());
        transaction.setSubtype(entity.getSubtype());
        transaction.setDescription(entity.getDescription());
        return transaction;
    }

    public static TransactionEntity toEntity(Transaction domain, UserEntity user){
       return new TransactionEntity(
               domain.getId(),
               user,
               domain.getValue(),
               domain.getDate(),
               domain.getType(),
               domain.getSubtype(),
               domain.getDescription()
        );
    }

    public static Transaction toDomain(TransactionRequestDto dto){
        Transaction transaction = new Transaction();
        transaction.setDate(dto.date());
        transaction.setUserId(dto.userId());
        transaction.setType(dto.type());
        transaction.setValue(dto.value());
        transaction.setSubtype(dto.subtype());
        transaction.setDescription(dto.description());
        return transaction;
    }

    public static TransactionResponseDto toResponseDto(Transaction transaction){
        return new TransactionResponseDto(
                transaction.getId(),
                transaction.getUserId(),
                transaction.getValue(),
                transaction.getDate(),
                transaction.getType(),
                transaction.getSubtype(),
                transaction.getDescription()
        );
    }
}
