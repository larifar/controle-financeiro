package com.app.controle_financeiro.infra.controller;

import com.app.controle_financeiro.application.useCases.ICreateTransaction;
import com.app.controle_financeiro.application.useCases.IDeleteTransaction;
import com.app.controle_financeiro.application.useCases.ISaveTransaction;
import com.app.controle_financeiro.domain.entities.Transaction;
import com.app.controle_financeiro.infra.dto.TransactionRequestDto;
import com.app.controle_financeiro.infra.dto.TransactionResponseDto;
import com.app.controle_financeiro.infra.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionSaveController {
    @Autowired
    private ICreateTransaction createTransaction;

    @Autowired
    private ISaveTransaction saveTransaction;

    @Autowired
    private IDeleteTransaction deleteTransaction;

    @PostMapping
    public ResponseEntity<TransactionResponseDto> createTransaction(@RequestBody TransactionRequestDto requestDto){
        Transaction transaction = createTransaction.save(TransactionMapper.toDomain(requestDto));
        return ResponseEntity.ok(TransactionMapper.toResponseDto(transaction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTransaction(@RequestBody TransactionRequestDto requestDto, @PathVariable long id){
        Transaction transaction = TransactionMapper.toDomain(requestDto);
        transaction.setId(id);
        saveTransaction.save(transaction);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable("id") long id){
        deleteTransaction.delete(id);
        return ResponseEntity.noContent().build();
    }
}
