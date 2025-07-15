package bot.application.usecase;

import bot.domain.dto.TransactionDto;

import java.io.IOException;

public interface ISaveTransactionUC {
    String save(String message, long telegramId);
}
