package com.app.controle_financeiro.infra.dto;

import com.app.controle_financeiro.domain.entities.TransactionTypeEnum;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

public record TransactionRequestByDateDto(
        LocalDateTime from,
        LocalDateTime until,
        long userId,
        TransactionTypeEnum type
) {
}
