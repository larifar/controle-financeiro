package bot.domain.exception;

public class BotException extends RuntimeException{
    private String code;

    public BotException(String message, String code) {
        super(message);
        this.code = code;
    }

    public BotException(ExceptionCodeEnums enumCode){
        super(enumCode.getMessage());
        this.code= enumCode.getCode();
    }

}
