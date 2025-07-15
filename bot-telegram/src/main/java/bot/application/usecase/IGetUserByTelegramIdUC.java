package bot.application.usecase;

import java.io.IOException;
import java.net.URISyntaxException;

public interface IGetUserByTelegramIdUC {
    long get(long telegramId) throws IOException;

}
