package com.app.controle_financeiro.infra.dto;

public record UserRequestDto(
        long telegramId,
        String name,
        String email,
        String password
) {
}
