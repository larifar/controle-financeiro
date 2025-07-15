package bot;

import bot.application.usecase.IConnectionToApiUC;
import bot.application.usecase.IGetUserByTelegramIdUC;
import bot.application.usecase.ISaveTransactionUC;
import bot.application.usecase.ITransformStringToTransactionDtoUC;
import bot.infra.implementations.ConnectionToApiUCImpl;
import bot.infra.implementations.GetUserUCByTelegramIdImpl;
import bot.infra.implementations.SaveTransactionUCImpl;
import bot.infra.implementations.TransformStringToTransactionUCImpl;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws TelegramApiException, IOException {
        Properties props = new Properties();
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            props.load(input);
        }

        String API_URL = "http://localhost:8081/";

        String botToken = props.getProperty("telegram.bot.token");

        IConnectionToApiUC connectionToApiUC = new ConnectionToApiUCImpl();
        IGetUserByTelegramIdUC getUserByTelegramIdUC = new GetUserUCByTelegramIdImpl(connectionToApiUC, API_URL+"users/telegram/");
        ITransformStringToTransactionDtoUC transactionDtoUC = new TransformStringToTransactionUCImpl();

        ISaveTransactionUC saveTransactionUC = new SaveTransactionUCImpl(
                connectionToApiUC, getUserByTelegramIdUC, transactionDtoUC, API_URL + "transactions"
        );

        try (TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication()) {
            botsApplication.registerBot(botToken, new MyBot(botToken, saveTransactionUC));
            System.out.println("MyAmazingBot successfully started!");

            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
