package com.app.controle_financeiro.domain.exceptions;

public class TransactionNotFoundException extends RuntimeException{
    public String code;

    public TransactionNotFoundException(String msg, String code) {
        super(msg);
        this.code = code;
    }
}
