package bot;

import bot.transactions.TransactionDto;
import bot.transactions.TransactionEnums;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyBot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;

    public MyBot(String token) {
        telegramClient = new OkHttpTelegramClient(token);
    }

    @Override
    public void consume(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            TransactionDto dto = messageToTransactionDto(message_text, chat_id);
            boolean persists = sendTransactionToApi(dto);
            String msgToUser = messageFrompersistence(persists);

            SendMessage message = SendMessage // Create a message object
                    .builder()
                    .chatId(chat_id)
                    .text(String.valueOf(msgToUser))
                    .build();
            try {
                telegramClient.execute(message); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private TransactionDto messageToTransactionDto(String message, long user) {
        message = message.toLowerCase();

       TransactionEnums type = getType(message);

        BigDecimal value = getValue(message);

        String subtype = getSubtype(message);

        return new TransactionDto(
                user, value, LocalDateTime.now(), type, subtype, ""
        );
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

    private String messageFrompersistence(boolean persist){
        if (persist){
            return "Persistido!";
        }
        return "Erro ao persistir";
    }

    private boolean sendTransactionToApi(TransactionDto dto) {
        try {
            URL url = new URL("http://localhost:8081/transactions");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            String json = mapper.writeValueAsString(dto);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            System.out.println("API response: " + responseCode);
            if (responseCode> 200 && responseCode < 400){
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
