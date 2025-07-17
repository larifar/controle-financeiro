package com.app.controle_financeiro.unit.usecases;

import com.app.controle_financeiro.application.implementations.FindUserByIdImpl;
import com.app.controle_financeiro.application.repository.IUserRepository;
import com.app.controle_financeiro.application.useCases.IFindUserById;
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
public class FindUserByIdUseCaseTest {
    @Mock
    IUserRepository userRepository;

    @Test
    void shouldThrowWhenUserNotFound(){
        IFindUserById findUserById = new FindUserByIdImpl(userRepository);
        long userId = 99L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, ()-> findUserById.find(userId));
    }

    @Test
    void shouldReturnUserWhenFoundById(){
        IFindUserById findUserById = new FindUserByIdImpl(userRepository);
        long userId = 99L;

        User user = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = findUserById.find(userId);

        assertEquals(user, result);
        verify(userRepository).findById(userId);
    }
}
