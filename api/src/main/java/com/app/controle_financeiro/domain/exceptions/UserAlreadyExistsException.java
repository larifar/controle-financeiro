package com.app.controle_financeiro.domain.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    public String code;

    public UserAlreadyExistsException(String msg, String code) {
        super(msg);
        this.code = code;
    }
}
