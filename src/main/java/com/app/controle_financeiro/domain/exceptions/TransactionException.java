package com.app.controle_financeiro.domain.exceptions;

public class TransactionException extends RuntimeException{
    public String code;

    public TransactionException(String msg, String code) {
        super(msg);
        this.code = code;
    }
}
