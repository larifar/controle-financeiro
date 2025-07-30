package bot;

import bot.application.usecase.*;
import bot.infra.implementations.*;
import okhttp3.OkHttpClient;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {
        Properties props = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            props.load(input);
        }

        String API_URL = System.getenv("API_URL");

        String botToken = System.getenv("BOT_TOKEN");
        if (botToken == null || botToken.isEmpty()) {
            InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties");
            if (input != null) {
                props.load(input);
                botToken = props.getProperty("telegram.bot.token");
            }
        }

        TelegramClient telegramClient = new OkHttpTelegramClient(botToken);

        IConnectionToApiUC connectionToApiUC = new ConnectionToApiUCImpl(new OkHttpClient());
        IGetUserByTelegramIdUC getUserByTelegramIdUC = new GetUserUCByTelegramIdImpl(connectionToApiUC, API_URL+"users/telegram/");
        ITransformStringToTransactionDtoUC transactionDtoUC = new TransformStringToTransactionUCImpl();

        ISaveTransactionUC saveTransactionUC = new SaveTransactionUCImpl(
                connectionToApiUC, getUserByTelegramIdUC, transactionDtoUC, API_URL
        );

        IGetListTransactionsUC getListTransactionsUC = new GetListTransactionsUCImpl(connectionToApiUC, API_URL, getUserByTelegramIdUC);

        try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
            MyBot bot = new MyBot(telegramClient, saveTransactionUC, getListTransactionsUC);
            bot.onRegister();
            botsApplication.registerBot(botToken, bot);
            System.out.println("MyAmazingBot successfully started!");

            Thread.currentThread().join();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


    }
}
