package bot.application.usecase;

import bot.domain.dto.TransactionDto;
import bot.domain.dto.TransactionEnums;
import bot.domain.exception.BotException;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;

public interface IGetListTransactionsUC {
    List<TransactionDto> get(LocalDateTime from,LocalDateTime until,long telegramId, TransactionEnums type) throws BotException, MalformedURLException, JsonProcessingException;
}
