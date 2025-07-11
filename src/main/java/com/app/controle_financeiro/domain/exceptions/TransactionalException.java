package com.app.controle_financeiro.domain.exceptions;

public class TransactionalException extends RuntimeException{
    public String code;

    public TransactionalException(String msg, String code) {
        super(msg);
        this.code = code;
    }
}
