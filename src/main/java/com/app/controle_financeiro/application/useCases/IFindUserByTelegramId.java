package com.app.controle_financeiro.application.useCases;

import com.app.controle_financeiro.domain.entities.User;
import com.app.controle_financeiro.domain.exceptions.UserNotFoundException;

public interface IFindUserByTelegramId {
    User find(long telegramId) throws UserNotFoundException;
}
