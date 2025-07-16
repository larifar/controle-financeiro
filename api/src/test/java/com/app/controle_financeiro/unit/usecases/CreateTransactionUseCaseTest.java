package com.app.controle_financeiro.unit.usecases;

import com.app.controle_financeiro.application.implementations.CreateTransactionImpl;
import com.app.controle_financeiro.application.repository.ITransactionRepository;
import com.app.controle_financeiro.application.repository.IUserRepository;
import com.app.controle_financeiro.domain.entities.Transaction;
import com.app.controle_financeiro.domain.entities.TransactionTypeEnum;
import com.app.controle_financeiro.domain.entities.User;
import com.app.controle_financeiro.domain.exceptions.TransactionException;
import com.app.controle_financeiro.domain.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CreateTransactionUseCaseTest {

    @Mock
    ITransactionRepository transactionRepository;

    @Mock
    IUserRepository userRepository;

    @Test
    void shoudlNotCreateTransactionWhenUserNotFound(){
        Transaction transaction = new Transaction();
        CreateTransactionImpl createTransaction = new CreateTransactionImpl(transactionRepository, userRepository);

        transaction.setUserId(99L);
        transaction.setValue(new BigDecimal(10));
        transaction.setType(TransactionTypeEnum.GASTO);
        when(userRepository.findById(99L)).thenReturn(Optional.empty());


        assertThrows(UserNotFoundException.class, ()-> createTransaction.save(transaction));
    }

    @Test
    void shoudlNotCreateTransactionWithoutUserId(){
        Transaction transaction = new Transaction();
        CreateTransactionImpl createTransaction = new CreateTransactionImpl(transactionRepository, userRepository);

        transaction.setValue(new BigDecimal(10));
        transaction.setType(TransactionTypeEnum.GASTO);


        assertThrows(UserNotFoundException.class, ()-> createTransaction.save(transaction));
    }

    @Test
    void shouldNotCreateTransactionWhenValueNegative(){
        Transaction transaction = new Transaction();
        User user = mock(User.class);
        CreateTransactionImpl createTransaction = new CreateTransactionImpl(transactionRepository, userRepository);

        transaction.setValue(new BigDecimal(-10));
        transaction.setType(TransactionTypeEnum.GASTO);
        transaction.setUserId(99L);
        when(userRepository.findById(99L)).thenReturn(Optional.of(user));

        assertThrows(TransactionException.class, () -> createTransaction.save(transaction));
    }

    @Test
    void shouldNotCreateTransactionWhenValueNull(){
        Transaction transaction = new Transaction();
        User user = mock(User.class);
        CreateTransactionImpl createTransaction = new CreateTransactionImpl(transactionRepository, userRepository);

        transaction.setType(TransactionTypeEnum.GASTO);
        transaction.setUserId(99L);
        when(userRepository.findById(99L)).thenReturn(Optional.of(user));

        assertThrows(TransactionException.class, () -> createTransaction.save(transaction));
    }

    @Test
    void shouldCreateTransactionWhenValid(){
        Transaction transaction = new Transaction();
        User user = mock(User.class);
        CreateTransactionImpl createTransaction = new CreateTransactionImpl(transactionRepository, userRepository);

        transaction.setValue(new BigDecimal(10));
        transaction.setType(TransactionTypeEnum.GASTO);
        transaction.setUserId(99L);
        when(userRepository.findById(99L)).thenReturn(Optional.of(user));
        when(transactionRepository.save(transaction)).thenReturn(transaction);

        var result = createTransaction.save(transaction);

        assertEquals(transaction, result);
        assertNotNull(result.getDate());
        verify(transactionRepository).save(transaction);
    }
}
