package com.app.controle_financeiro.infra.config;

import com.app.controle_financeiro.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionGlobalHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: "+ex.code+" : "+ex.getMessage());
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<String> handleTransactionNotFound(TransactionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro: "+ex.code+" : "+ex.getMessage());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> handleUserGeneralException(UserException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: "+ex.code+" : "+ex.getMessage());
    }

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<String> handleTransactionGeneralException(TransactionException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: "+ex.code+" : "+ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: "+ex.code+" : "+ex.getMessage());
    }

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<String> handleDateTime(DateTimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: "+ex.code+" : "+ex.getMessage());
    }
}
