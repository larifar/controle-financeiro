package com.app.controle_financeiro.unit.usecases;

import com.app.controle_financeiro.application.implementations.SaveTransactionImpl;
import com.app.controle_financeiro.application.repository.ITransactionRepository;
import com.app.controle_financeiro.application.repository.IUserRepository;
import com.app.controle_financeiro.application.useCases.ISaveTransaction;
import com.app.controle_financeiro.domain.entities.Transaction;
import com.app.controle_financeiro.domain.entities.User;
import com.app.controle_financeiro.domain.exceptions.ExceptionCodeEnum;
import com.app.controle_financeiro.domain.exceptions.TransactionException;
import com.app.controle_financeiro.domain.exceptions.TransactionNotFoundException;
import com.app.controle_financeiro.domain.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class SaveTransactionUseCaseTest {

    @Mock
    ITransactionRepository transactionRepository;
    @Mock
    IUserRepository userRepository;

    @Test
    void shouldThrowIfIdPresentAndNotFound(){
        ISaveTransaction saveTransaction = new SaveTransactionImpl(transactionRepository, userRepository);
        Transaction transaction = new Transaction();
        transaction.setId(99L);

        when(transactionRepository.findById(99L)).thenReturn(Optional.empty());

        TransactionNotFoundException ex = assertThrows(TransactionNotFoundException.class, ()-> saveTransaction.save(transaction));
        assertEquals(ExceptionCodeEnum.TRA001.getCode(), ex.code);

        verify(transactionRepository).findById((99L));
    }

    @Test
    void shouldThrowIfUserIdNotPresent(){
        ISaveTransaction saveTransaction = new SaveTransactionImpl(transactionRepository, userRepository);
        Transaction transaction = new Transaction();


        TransactionException ex = assertThrows(TransactionException.class, ()-> saveTransaction.save(transaction));
        assertEquals(ExceptionCodeEnum.TRA003.getCode(), ex.code);
    }

    @Test
    void shouldThrowIfUserIdNotFound(){
        ISaveTransaction saveTransaction = new SaveTransactionImpl(transactionRepository, userRepository);
        Transaction transaction = new Transaction();
        transaction.setUserId(99L);

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        UserNotFoundException ex = assertThrows(UserNotFoundException.class, ()-> saveTransaction.save(transaction));
        assertEquals(ExceptionCodeEnum.USER01.getCode(), ex.code);
        verify(userRepository).findById(99L);
    }

    @Test
    void shouldThrowIfValueNotPresent(){
        ISaveTransaction saveTransaction = new SaveTransactionImpl(transactionRepository, userRepository);
        Transaction transaction = new Transaction();
        transaction.setUserId(99L);

        when(userRepository.findById(99L)).thenReturn(Optional.of(mock(User.class)));

        TransactionException ex = assertThrows(TransactionException.class, ()-> saveTransaction.save(transaction));
        assertEquals(ExceptionCodeEnum.TRA002.getCode(), ex.code);

        verify(userRepository).findById(99L);
    }

    @Test
    void shouldThrowIfValueNegative(){
        ISaveTransaction saveTransaction = new SaveTransactionImpl(transactionRepository, userRepository);
        Transaction transaction = new Transaction();
        transaction.setUserId(99L);
        transaction.setValue(new BigDecimal(-10));

        when(userRepository.findById(99L)).thenReturn(Optional.of(mock(User.class)));

        TransactionException ex = assertThrows(TransactionException.class, ()-> saveTransaction.save(transaction));
        assertEquals(ExceptionCodeEnum.TRA002.getCode(), ex.code);
        verify(userRepository).findById(99L);
    }

    @Test
    void shouldSaveAndSetDateIfValid(){
        ISaveTransaction saveTransaction = new SaveTransactionImpl(transactionRepository, userRepository);
        Transaction transaction = new Transaction();
        transaction.setUserId(99L);
        transaction.setValue(new BigDecimal(10));

        when(userRepository.findById(99L)).thenReturn(Optional.of(mock(User.class)));

        saveTransaction.save(transaction);
        assertNotNull(transaction.getDate());
        verify(userRepository).findById(99L);

    }

    @Test
    void shouldSaveAndNotOverwriteIfDatePresent(){
        ISaveTransaction saveTransaction = new SaveTransactionImpl(transactionRepository, userRepository);
        Transaction transaction = new Transaction();
        transaction.setUserId(99L);
        transaction.setValue(new BigDecimal(10));
        LocalDateTime date = LocalDateTime.now().minusDays(1);
        transaction.setDate(date);

        when(userRepository.findById(99L)).thenReturn(Optional.of(mock(User.class)));

        saveTransaction.save(transaction);
        assertEquals(date, transaction.getDate());
        verify(userRepository).findById(99L);

    }
}
