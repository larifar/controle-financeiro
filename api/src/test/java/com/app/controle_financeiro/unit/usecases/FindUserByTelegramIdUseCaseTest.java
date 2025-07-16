package com.app.controle_financeiro.unit.usecases;

import com.app.controle_financeiro.application.implementations.FindUserByIdImpl;
import com.app.controle_financeiro.application.implementations.FindUserByTelegramIdImpl;
import com.app.controle_financeiro.application.repository.IUserRepository;
import com.app.controle_financeiro.application.useCases.IFindUserById;
import com.app.controle_financeiro.application.useCases.IFindUserByTelegramId;
import com.app.controle_financeiro.domain.entities.User;
import com.app.controle_financeiro.domain.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class FindUserByTelegramIdUseCaseTest {
    @Mock
    IUserRepository userRepository;

    @Test
    void shouldThrowWhenUserNotFound(){
        IFindUserByTelegramId findUserByTelegramId = new FindUserByTelegramIdImpl(userRepository);
        long telegramId = 99999L;

        when(userRepository.findByTelegramId(telegramId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, ()-> findUserByTelegramId.find(telegramId));
    }

    @Test
    void shouldReturnUserWhenFoundById(){
        IFindUserByTelegramId findUserByTelegramId = new FindUserByTelegramIdImpl(userRepository);
        long telegramId = 99999L;

        User user = new User();

        when(userRepository.findByTelegramId(telegramId)).thenReturn(Optional.of(user));

        User result = findUserByTelegramId.find(telegramId);

        assertEquals(user, result);
        verify(userRepository).findByTelegramId(telegramId);
    }
}
