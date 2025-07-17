package com.app.controle_financeiro.unit.usecases;

import com.app.controle_financeiro.application.implementations.FindTransactionByIdImpl;
import com.app.controle_financeiro.application.repository.ITransactionRepository;
import com.app.controle_financeiro.application.useCases.IFindTransactionById;
import com.app.controle_financeiro.domain.entities.Transaction;
import com.app.controle_financeiro.domain.exceptions.TransactionNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class FindTransactionByIdUseCaseTest {

    @Mock
    ITransactionRepository transactionRepository;

    @Test
    void shouldThrowWhenTransactionNotFound(){
        IFindTransactionById findTransactionById = new FindTransactionByIdImpl(transactionRepository);
        long transactionId = 99L;

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class, ()-> findTransactionById.find(transactionId));
    }

    @Test
    void shouldReturnTransactionWhenFoundById(){
        IFindTransactionById findTransactionById = new FindTransactionByIdImpl(transactionRepository);
        long transactionId = 99L;
        Transaction transaction = new Transaction();
        transaction.setId(transactionId);

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
        Transaction result = findTransactionById.find(transactionId);

        assertEquals(transaction, result);
        verify(transactionRepository).findById(transactionId);
    }
}
