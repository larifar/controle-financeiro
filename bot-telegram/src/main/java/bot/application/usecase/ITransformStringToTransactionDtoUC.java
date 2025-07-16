package bot.application.usecase;

import bot.domain.dto.TransactionDto;

public interface ITransformStringToTransactionDtoUC {
    TransactionDto transform(String message, long userId);
}
