package com.app.controle_financeiro.unit.usecases;

import com.app.controle_financeiro.application.implementations.DeleteUserImpl;
import com.app.controle_financeiro.application.repository.ITransactionRepository;
import com.app.controle_financeiro.application.repository.IUserRepository;
import com.app.controle_financeiro.application.useCases.IDeleteUser;
import com.app.controle_financeiro.domain.entities.User;
import com.app.controle_financeiro.domain.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class DeleteUserUseCaseTest {

    @Mock
    IUserRepository userRepository;
    @Mock
    ITransactionRepository transactionRepository;

    @Test
    void shouldNotDeleteWhenUserNotFound(){
        IDeleteUser deleteUser = new DeleteUserImpl(userRepository, transactionRepository);
        long userId = 99L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, ()-> deleteUser.delete(userId));
    }

    @Test
    void shouldDeleteUserWhenIdFoundAndDeleteAllTransactions(){
        IDeleteUser deleteUser = new DeleteUserImpl(userRepository, transactionRepository);
        long userId = 99L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(mock(User.class)));

        deleteUser.delete(userId);

        verify(transactionRepository).deleteAll(userId);
        verify(userRepository).delete(userId);
    }
}
