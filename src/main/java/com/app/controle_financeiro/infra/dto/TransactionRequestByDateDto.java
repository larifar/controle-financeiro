package com.app.controle_financeiro.infra.dto;

import com.app.controle_financeiro.domain.entities.TransactionTypeEnum;

import java.time.LocalDateTime;

public record TransactionRequestByDateDto(
        LocalDateTime from,
        LocalDateTime until,
        long userId,
        TransactionTypeEnum type
) {
}
