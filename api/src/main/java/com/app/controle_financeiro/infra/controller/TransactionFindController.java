package com.app.controle_financeiro.infra.controller;

import com.app.controle_financeiro.application.useCases.IFindTransactionById;
import com.app.controle_financeiro.application.useCases.IGetTransactionsByTypeAndByPeriod;
import com.app.controle_financeiro.domain.entities.Transaction;
import com.app.controle_financeiro.domain.entities.TransactionTypeEnum;
import com.app.controle_financeiro.infra.dto.TransactionRequestByDateDto;
import com.app.controle_financeiro.infra.dto.TransactionResponseDto;
import com.app.controle_financeiro.infra.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    @GetMapping("/list")
    public ResponseEntity<List<TransactionResponseDto>> findList(
            @RequestParam("userId") long userId, @RequestParam("from")LocalDateTime from,
            @RequestParam("until")LocalDateTime until, @RequestParam(value = "type", required = false)TransactionTypeEnum type){

        List<Transaction> list = getTransactionsByTypeAndByPeriod.getTransactions(from, until, userId, type);
        return ResponseEntity.ok(list.stream().map(TransactionMapper::toResponseDto).toList());
    }

    @GetMapping("/list/timestamp")
    public ResponseEntity<List<TransactionResponseDto>> findListTimestamp(
            @RequestParam("userId") long userId, @RequestParam("from") long from,
            @RequestParam("until") long until, @RequestParam(value = "type", required = false)TransactionTypeEnum type) {

        LocalDateTime fromLocalDT = LocalDateTime.ofInstant(Instant.ofEpochSecond(from), ZoneId.systemDefault());
        LocalDateTime untilLocalDT = LocalDateTime.ofInstant(Instant.ofEpochSecond(until), ZoneId.systemDefault());

        List<Transaction> list = getTransactionsByTypeAndByPeriod.getTransactions(fromLocalDT, untilLocalDT, userId, type);
        return ResponseEntity.ok(list.stream().map(TransactionMapper::toResponseDto).toList());
    }
}
