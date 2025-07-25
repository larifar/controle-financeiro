package bot;

import bot.application.usecase.ISaveTransactionUC;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

public class MyBot implements LongPollingSingleThreadUpdateConsumer {
    private final TelegramClient telegramClient;
    private final ISaveTransactionUC saveTransactionUC;

    public MyBot(String token, ISaveTransactionUC saveTransactionUC) {
        telegramClient = new OkHttpTelegramClient(token);
        this.saveTransactionUC = saveTransactionUC;
    }

    @Override
    public void consume(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();


            String msgToUser = saveTransactionUC.save(message_text, chat_id);

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

}
