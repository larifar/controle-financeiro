package com.app.controle_financeiro.domain.exceptions;

public enum ExceptionCodeEnum {

    TRA001("Transação não encontrada!", "TRA-001"),
    TRA002("Transação não pode ter valor nulo/menor que zero!", "TRA-002"),
    TRA003("Transação deve estar relacionada a um usuário!", "TRA-003"),
    USER01("Usuário não encontrado!", "USER-01"),
    USER02("Usuário já registrado", "USER-02"),
    USER03("Erro ao criar usuário!", "USER-03"),
    USER04("Erro ao salvar usuário!", "USER-04"),
    DATE01("Formato de data inválido!", "DATE-01"),
    DATE02("Data não pode ser nula!", "DATE-02")
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
