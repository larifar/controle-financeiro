package com.app.controle_financeiro.domain.exceptions;

public enum ExceptionCodeEnum {

    TRA001("Transação não encontrada!", "TRA-001");
    private String message;
    private String code;

    ExceptionCodeEnum(String message, String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
