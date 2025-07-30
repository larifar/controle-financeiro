package bot.infra.implementations;

import bot.application.usecase.IConnectionToApiUC;
import bot.application.usecase.IGetUserByTelegramIdUC;
import bot.application.usecase.ISaveTransactionUC;
import bot.application.usecase.ITransformStringToTransactionDtoUC;
import bot.domain.dto.TransactionDto;
import bot.domain.exception.BotException;
import bot.domain.exception.BotUserNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URL;

public class SaveTransactionUCImpl implements ISaveTransactionUC {
    private final IConnectionToApiUC connection;
    private final IGetUserByTelegramIdUC getUserByTelegramIdUC;

    private final ITransformStringToTransactionDtoUC stringToTransactionDto;

    private final String API_URL;

    private final ObjectMapper mapper = new ObjectMapper();

    public SaveTransactionUCImpl(IConnectionToApiUC connection, IGetUserByTelegramIdUC getUserByTelegramIdUC, ITransformStringToTransactionDtoUC stringToTransactionDto, String apiUrl) {
        this.connection = connection;
        this.getUserByTelegramIdUC = getUserByTelegramIdUC;
        this.stringToTransactionDto = stringToTransactionDto;
        API_URL = apiUrl;
    }

    @Override
    public String save(String message, long telegramId){
        try {
            long userId = getUserByTelegramIdUC.get(telegramId);

            TransactionDto dto = stringToTransactionDto.transform(message, userId);
            mapper.registerModule(new JavaTimeModule());
            String json = mapper.writeValueAsString(dto);

            URL url = new URL(API_URL + "transactions");

            connection.post(url, json);
            return dto.type() + " salvo com sucesso!";

        }catch (BotUserNotFoundException e){
            return "Usuário não cadastrado! Cadastre-se em: " + API_URL +"login";
        }
        catch (BotException | IOException e){
            e.printStackTrace();
            return "Erro ao salvar: " + e.getMessage();
        }
    }
}
