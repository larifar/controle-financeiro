package bot.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TransactionDto(
        long userId,
        BigDecimal value,
        LocalDateTime date,
        TransactionEnums type,
        String subtype,
        String description
) {
}
