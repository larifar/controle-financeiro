package com.app.controle_financeiro.domain.exceptions;

public class UserException extends RuntimeException{
    public String code;

    public UserException(String msg, String code) {
        super(msg);
        this.code = code;
    }
}
