package bot.application.usecase;

import bot.domain.exception.BotException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

public interface IGetUserByTelegramIdUC {
    long get(long telegramId) throws BotException, MalformedURLException;

}
