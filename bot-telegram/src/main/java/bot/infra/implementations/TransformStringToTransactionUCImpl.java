package bot.infra.implementations;

import bot.application.usecase.ITransformStringToTransactionDtoUC;
import bot.domain.dto.TransactionDto;
import bot.domain.dto.TransactionEnums;

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

    private BigDecimal getValue(String message){
        String value;
        Pattern pattern = Pattern.compile("r\\$\\s*(\\d+[\\,\\.]?\\d*)");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            value = matcher.group(1);
        } else {
            throw new IllegalArgumentException("Valor não encontrado na mensagem!");
        }

        String digits = value.replaceAll("[^\\d]", "");
        if (digits.isEmpty()) {
            throw new IllegalArgumentException("Valor inválido: nenhum dígito encontrado.");
        }
        if (digits.length() == 1) {
            digits = digits + "00";
        } else if (digits.length() == 2) {
            digits = digits + "0";
        }
        return new BigDecimal(digits).movePointLeft(2);
    }

    private TransactionEnums getType(String message){
        if (message.contains("gastei") || message.contains("gasto") || message.contains("comprei")) {
            return TransactionEnums.GASTO;
        }
        if (message.contains("recebi") || message.contains("ganhei")) {
            return TransactionEnums.RECEITA;
        }
        return null;
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
