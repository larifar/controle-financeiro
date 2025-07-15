package bot.infra.implementations;

import bot.application.usecase.ITransformStringToTransactionDtoUC;
import bot.domain.dto.TransactionDto;
import bot.domain.dto.TransactionEnums;
import bot.domain.exception.BotException;
import bot.domain.exception.ExceptionCodeEnums;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransformStringToTransactionUCImpl implements ITransformStringToTransactionDtoUC {
    @Override
    public TransactionDto transform(String message, long userId) {
        message = message.toLowerCase();

        TransactionEnums type = getType(message);

        BigDecimal value = getValue(message);

        String subtype = getSubtype(message);

        return new TransactionDto(
                userId, value, LocalDateTime.now(), type, subtype, ""
        );
    }

    private BigDecimal getValue(String message) {
        Pattern pattern = Pattern.compile("r\\$\\s*(\\d+[\\.,]?\\d*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            String raw = matcher.group(1).replace(",", ".");
            return new BigDecimal(raw);
        } else {
            throw new BotException(ExceptionCodeEnums.VALUE_NOT_FOUND);
        }
    }

    private TransactionEnums getType(String message){
        if (message.contains("gastei") || message.contains("gasto") || message.contains("comprei")) {
            return TransactionEnums.GASTO;
        }
        if (message.contains("recebi") || message.contains("ganhei")) {
            return TransactionEnums.RECEITA;
        }
        else {
            throw new BotException(ExceptionCodeEnums.TYPE_NOT_FOUND);
        }
    }

    private String getSubtype(String message){
        Pattern pattern = Pattern.compile("(?:r\\$\\s*[\\d.,]+|em|de)\\s*([^\\d\\W][\\w\\s]*)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(message);
        String texto = "";

        if (matcher.find()) {
            texto = matcher.group(1).trim();
        }
        return texto;
    }
}
