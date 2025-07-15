package com.app.controle_financeiro.domain.exceptions;

public class UserNotFoundException extends RuntimeException{
    public String code;

    public UserNotFoundException(String msg, String code) {
        super(msg);
        this.code = code;
    }
}
