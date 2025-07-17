package com.app.controle_financeiro.unit.usecases;

import com.app.controle_financeiro.application.implementations.DeleteAllTransactionsImpl;
import com.app.controle_financeiro.application.repository.ITransactionRepository;
import com.app.controle_financeiro.application.repository.IUserRepository;
import com.app.controle_financeiro.application.useCases.IDeleteAllTransactions;
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
public class DeleteAllTransactionsUseCaseTest {
    @Mock
    ITransactionRepository transactionRepository;

    @Mock
    IUserRepository userRepository;

    @Test
    void shouldThrowWhenUserIdNotFound(){
        IDeleteAllTransactions deleteAllTransactions = new DeleteAllTransactionsImpl(transactionRepository, userRepository);
        long userId = 99L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, ()-> deleteAllTransactions.deleteAllByUserId(userId));
    }

    @Test
    void shouldDeleteAllTransactionsWhenUserFound(){
        IDeleteAllTransactions deleteAllTransactions = new DeleteAllTransactionsImpl(transactionRepository, userRepository);
        long userId = 99L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(mock(User.class)));

        deleteAllTransactions.deleteAllByUserId(userId);

        verify(transactionRepository).deleteAll(userId);
    }
}
