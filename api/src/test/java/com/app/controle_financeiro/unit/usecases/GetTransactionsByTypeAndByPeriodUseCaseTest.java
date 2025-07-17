package com.app.controle_financeiro.unit.usecases;

import com.app.controle_financeiro.application.implementations.GetTransactionsByTypeAndByPeriodImpl;
import com.app.controle_financeiro.application.repository.ITransactionRepository;
import com.app.controle_financeiro.application.repository.IUserRepository;
import com.app.controle_financeiro.application.useCases.IGetTransactionsByTypeAndByPeriod;
import com.app.controle_financeiro.domain.entities.Transaction;
import com.app.controle_financeiro.domain.entities.TransactionTypeEnum;
import com.app.controle_financeiro.domain.entities.User;
import com.app.controle_financeiro.domain.exceptions.DateTimeException;
import com.app.controle_financeiro.domain.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class GetTransactionsByTypeAndByPeriodUseCaseTest {

    @Mock
    ITransactionRepository transactionRepository;
    @Mock
    IUserRepository userRepository;

    @Test
    void shouldThrowWhenUserNotFound(){
        IGetTransactionsByTypeAndByPeriod getTransactionsByTypeAndByPeriod = new GetTransactionsByTypeAndByPeriodImpl(transactionRepository, userRepository);
        long userId = 99L;
        LocalDateTime from = LocalDateTime.of(2025, 7,10, 7,0);
        LocalDateTime until = LocalDateTime.of(2025, 7,11, 7,0);
        TransactionTypeEnum type = TransactionTypeEnum.GASTO;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                ()-> getTransactionsByTypeAndByPeriod.getTransactions(from, until, userId, type));
    }

    @Test
    void shouldThrowWhenWithoutFromDate(){
        IGetTransactionsByTypeAndByPeriod getTransactionsByTypeAndByPeriod = new GetTransactionsByTypeAndByPeriodImpl(transactionRepository, userRepository);
        long userId = 99L;
        LocalDateTime from = null;
        LocalDateTime until = LocalDateTime.of(2025, 7,11, 7,0);
        TransactionTypeEnum type = TransactionTypeEnum.GASTO;

        when(userRepository.findById(userId)).thenReturn(Optional.of(mock(User.class)));

        assertThrows(DateTimeException.class,
                ()-> getTransactionsByTypeAndByPeriod.getTransactions(from, until, userId, type));
    }

    @Test
    void shouldThrowWhenWithoutUntilDate(){
        IGetTransactionsByTypeAndByPeriod getTransactionsByTypeAndByPeriod = new GetTransactionsByTypeAndByPeriodImpl(transactionRepository, userRepository);
        long userId = 99L;
        LocalDateTime from = LocalDateTime.of(2025, 7,10, 7,0);
        LocalDateTime until = null;
        TransactionTypeEnum type = TransactionTypeEnum.GASTO;

        when(userRepository.findById(userId)).thenReturn(Optional.of(mock(User.class)));

        assertThrows(DateTimeException.class,
                ()-> getTransactionsByTypeAndByPeriod.getTransactions(from, until, userId, type));
    }

    @Test
    void shouldThrowWhenDateFromisAfterUntil(){
        IGetTransactionsByTypeAndByPeriod getTransactionsByTypeAndByPeriod = new GetTransactionsByTypeAndByPeriodImpl(transactionRepository, userRepository);
        long userId = 99L;
        LocalDateTime from = LocalDateTime.of(2025, 7,12, 7,0);
        LocalDateTime until = LocalDateTime.of(2025, 7,11, 7,0);
        TransactionTypeEnum type = TransactionTypeEnum.GASTO;

        when(userRepository.findById(userId)).thenReturn(Optional.of(mock(User.class)));

        assertThrows(DateTimeException.class,
                ()-> getTransactionsByTypeAndByPeriod.getTransactions(from, until, userId, type));
    }

    @Test
    void shouldReturnListTransactionWhenValid(){
        IGetTransactionsByTypeAndByPeriod getTransactionsByTypeAndByPeriod = new GetTransactionsByTypeAndByPeriodImpl(transactionRepository, userRepository);
        long userId = 99L;
        LocalDateTime from = LocalDateTime.of(2025, 7,10, 7,0);
        LocalDateTime until = LocalDateTime.of(2025, 7,11, 7,0);
        TransactionTypeEnum type = TransactionTypeEnum.GASTO;

        List<Transaction> list = Arrays.asList(mock(Transaction.class), mock(Transaction.class));

        when(userRepository.findById(userId)).thenReturn(Optional.of(mock(User.class)));
        when(transactionRepository.getFromDateUntilDatebyType(from, until, userId, type)).thenReturn(list);

        List<Transaction> result = getTransactionsByTypeAndByPeriod.getTransactions(from, until, userId, type);

        assertEquals(result, list);
        verify(userRepository).findById(userId);
        verify(transactionRepository).getFromDateUntilDatebyType(from, until, userId, type);
    }
}
