package com.app.controle_financeiro.domain.exceptions;

public class TransactionalNotFoundException extends RuntimeException{
    public String code;

    public TransactionalNotFoundException(String msg, String code) {
        super(msg);
        this.code = code;
    }
}
