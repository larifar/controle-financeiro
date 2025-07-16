package bot.domain.exception;

public class BotUserNotFoundException extends RuntimeException{
    private String code;

    public BotUserNotFoundException(String message, String code) {
        super(message);
        this.code = code;
    }

    public BotUserNotFoundException(ExceptionCodeEnums enumCode){
        super(enumCode.getMessage());
        this.code= enumCode.getCode();
    }

}
