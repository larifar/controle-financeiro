package bot.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDto(
        long userId,
        BigDecimal value,
        LocalDateTime date,
        TransactionEnums type,
        String subtype,
        String description
) {
}
