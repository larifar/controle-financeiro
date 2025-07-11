package com.app.controle_financeiro.domain.exceptions;

public enum ExceptionCodeEnum {

    TRA001("Transação não encontrada!", "TRA-001"),
    TRA002("Transação não pode ter valor nulo/menor que zero!", "TRA-002"),
    USER01("Usuário não encontrado!", "USER-01")
    ;

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
