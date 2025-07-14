package com.app.controle_financeiro.infra.dto;

import com.app.controle_financeiro.domain.entities.TransactionTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDto(
        long id,
        long userId,
        BigDecimal value,
        LocalDateTime date,
        TransactionTypeEnum type,
        String subtype,
        String description
) {
}
