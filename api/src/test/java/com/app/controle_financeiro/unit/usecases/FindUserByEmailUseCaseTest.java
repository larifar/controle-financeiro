package com.app.controle_financeiro.unit.usecases;

import com.app.controle_financeiro.application.implementations.FindUserByEmailImpl;
import com.app.controle_financeiro.application.repository.IUserRepository;
import com.app.controle_financeiro.application.useCases.IFindUserByEmail;
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
public class FindUserByEmailUseCaseTest {
    @Mock
    IUserRepository userRepository;

    @Test
    void shouldThrowWhenUserNotFound(){
        IFindUserByEmail findUserByEmail = new FindUserByEmailImpl(userRepository);
        String email = "email@test.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, ()-> findUserByEmail.find(email));
    }

    @Test
    void shouldReturnUserWhenFoundByEmail(){
        IFindUserByEmail findUserByEmail = new FindUserByEmailImpl(userRepository);
        String email = "email@test.com";
        User user = new User();
        user.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User result = findUserByEmail.find(email);

        assertEquals(user, result);
        verify(userRepository).findByEmail(email);
    }
}
