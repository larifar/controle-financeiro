package bot.domain.exception;

public enum ExceptionCodeEnums {
    VALUE_NOT_FOUND("Valor não encontrado na mensagem!", "VALUE_NOT_FOUND"),
    TYPE_NOT_FOUND("Tipo não encontrado na mensagem!", "TYPE_NOT_FOUND"),
    USER_NOT_FOUND("Usuário não encontrado!", "USER_NOT_FOUND"),
    URI_FORMAT("Erro ao formar URI", "URI_FORMAT"),

    BOT_IO_EXCEPTION("Erro ao enviar para servidor!", "BOT_IO_EXCEPTION")

    ;

    private String message;
    private String code;

    ExceptionCodeEnums(String message, String code) {
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
