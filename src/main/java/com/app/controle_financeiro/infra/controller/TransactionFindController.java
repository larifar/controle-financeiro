package com.app.controle_financeiro.infra.controller;

import com.app.controle_financeiro.application.useCases.IFindTransactionById;
import com.app.controle_financeiro.application.useCases.IGetTransactionsByTypeAndByPeriod;
import com.app.controle_financeiro.domain.entities.Transaction;
import com.app.controle_financeiro.infra.dto.TransactionRequestByDateDto;
import com.app.controle_financeiro.infra.dto.TransactionResponseDto;
import com.app.controle_financeiro.infra.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionFindController {
    @Autowired
    private IFindTransactionById findTransactionById;

    @Autowired
    private IGetTransactionsByTypeAndByPeriod getTransactionsByTypeAndByPeriod;

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> findById(@PathVariable("id") long id){
        Transaction transaction = findTransactionById.find(id);
        return ResponseEntity.ok(TransactionMapper.toResponseDto(transaction));
    }

    @GetMapping()
    public ResponseEntity<List<TransactionResponseDto>> findList(@RequestBody TransactionRequestByDateDto request){
        List<Transaction> list = getTransactionsByTypeAndByPeriod.getTransactions(request.from(), request.until(), request.userId(), request.type());
        return ResponseEntity.ok(list.stream().map(TransactionMapper::toResponseDto).toList());
    }
}
