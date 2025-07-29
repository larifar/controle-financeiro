package bot.application.usecase;

import bot.domain.exception.BotException;

import java.net.MalformedURLException;

public interface IGetUserByTelegramIdUC {
    long get(long telegramId) throws BotException, MalformedURLException;

}
