package com.app.controle_financeiro.unit.usecases;

import com.app.controle_financeiro.application.implementations.DeleteTransactionImpl;
import com.app.controle_financeiro.application.repository.ITransactionRepository;
import com.app.controle_financeiro.application.useCases.IDeleteTransaction;
import com.app.controle_financeiro.domain.entities.Transaction;
import com.app.controle_financeiro.domain.exceptions.TransactionNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class DeleteTransactionUseCaseTests {

    @MockitoBean
    ITransactionRepository transactionRepository;

    @Test
    void shoulThrowWhenTransactionNotFound(){
        IDeleteTransaction deleteTransaction = new DeleteTransactionImpl(transactionRepository);
        long transactionId = 99L;

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class, () -> deleteTransaction.delete(transactionId));
    }

    @Test
    void shoulDeleteTransactionWhenIdFound() {
        IDeleteTransaction deleteTransaction = new DeleteTransactionImpl(transactionRepository);
        long transactionId = 99L;

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(mock(Transaction.class)));
        deleteTransaction.delete(transactionId);

        verify(transactionRepository).delete(transactionId);
    }
}
