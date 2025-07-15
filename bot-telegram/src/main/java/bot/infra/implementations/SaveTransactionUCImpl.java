package bot.infra.implementations;

import bot.application.usecase.IConnectionToApiUC;
import bot.application.usecase.IGetUserByTelegramIdUC;
import bot.application.usecase.ISaveTransactionUC;
import bot.application.usecase.ITransformStringToTransactionDtoUC;
import bot.domain.dto.TransactionDto;
import bot.domain.exception.BotException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URL;

public class SaveTransactionUCImpl implements ISaveTransactionUC {
    private final IConnectionToApiUC connection;
    private final IGetUserByTelegramIdUC getUserByTelegramIdUC;

    private final ITransformStringToTransactionDtoUC stringToTransactionDto;

    private final String API_URL;

    private ObjectMapper mapper = new ObjectMapper();

    public SaveTransactionUCImpl(IConnectionToApiUC connection, IGetUserByTelegramIdUC getUserByTelegramIdUC, ITransformStringToTransactionDtoUC stringToTransactionDto, String apiUrl) {
        this.connection = connection;
        this.getUserByTelegramIdUC = getUserByTelegramIdUC;
        this.stringToTransactionDto = stringToTransactionDto;
        API_URL = apiUrl;
    }

    @Override
    public String save(String message, long telegramId){
        String msg = "Erro ao salvar: ";
        try {
            long userId = getUserByTelegramIdUC.get(telegramId);

            TransactionDto dto = stringToTransactionDto.transform(message, userId);
            mapper.registerModule(new JavaTimeModule());
            String json = mapper.writeValueAsString(dto);

            URL url = new URL(API_URL);

            connection.post(url, json);
            return "Salvo com sucesso! = " + json;

        }catch (BotException | IOException e){
            return msg+= e.getMessage();
        }
    }
}
