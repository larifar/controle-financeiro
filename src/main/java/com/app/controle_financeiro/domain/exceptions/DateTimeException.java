package com.app.controle_financeiro.domain.exceptions;

public class DateTimeException extends RuntimeException{
    public String code;

    public DateTimeException(String msg, String code) {
        super(msg);
        this.code = code;
    }
}
