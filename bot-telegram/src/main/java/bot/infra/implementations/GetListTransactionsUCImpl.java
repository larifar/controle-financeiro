package bot.infra.implementations;

import bot.application.usecase.IConnectionToApiUC;
import bot.application.usecase.IGetListTransactionsUC;
import bot.application.usecase.IGetUserByTelegramIdUC;
import bot.domain.dto.TransactionDto;
import bot.domain.dto.TransactionEnums;
import bot.domain.exception.BotException;
import bot.domain.exception.ExceptionCodeEnums;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.HttpUrl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class GetListTransactionsUCImpl implements IGetListTransactionsUC {

    private final IConnectionToApiUC connection;
    private final String API_URL;

    private final IGetUserByTelegramIdUC getUserByTelegramIdUC;
    private final ObjectMapper mapper = new ObjectMapper();

    public GetListTransactionsUCImpl(IConnectionToApiUC connection, String apiUrl, IGetUserByTelegramIdUC getUserByTelegramIdUC) {
        this.connection = connection;
        API_URL = apiUrl;
        this.getUserByTelegramIdUC = getUserByTelegramIdUC;
        mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public List<TransactionDto> get(LocalDateTime from, LocalDateTime until, long telegramId, TransactionEnums type) throws BotException, MalformedURLException{

        long userId = getUserByTelegramIdUC.get(telegramId);

        HttpUrl.Builder builder = Objects.requireNonNull(HttpUrl.parse(API_URL + "transactions/list"))
                .newBuilder()
                .addQueryParameter("userId", String.valueOf(userId))
                .addQueryParameter("from", from.toString())
                .addQueryParameter("until", until.toString());

        if (type != null) {
            builder.addQueryParameter("type", type.name());
        }
        URL url = builder.build().url();

        try{
            var response = connection.get(url);
            return mapper.readValue(response,  new TypeReference<List<TransactionDto>>() {});

        }catch (IOException e){
            throw new BotException(ExceptionCodeEnums.BOT_IO_EXCEPTION);
        }
    }
}
