package bot.infra.implementations;

import bot.application.usecase.IConnectionToApiUC;
import bot.application.usecase.IGetUserByTelegramIdUC;
import bot.domain.dto.UserDto;
import bot.domain.exception.BotException;
import bot.domain.exception.BotUserNotFoundException;
import bot.domain.exception.ExceptionCodeEnums;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class GetUserUCByTelegramIdImpl implements IGetUserByTelegramIdUC {
    private final IConnectionToApiUC connection;
    private final String API_URL;
    private final ObjectMapper mapper = new ObjectMapper();


    public GetUserUCByTelegramIdImpl(IConnectionToApiUC connection, String apiUrl) {
        this.connection = connection;
        API_URL = apiUrl;
    }

    @Override
    public long get(long telegramId) throws BotException, MalformedURLException {
        URL url = new URL(API_URL + telegramId);

        try (var response = connection.get(url)){
            UserDto user = mapper.readValue(response, UserDto.class);
            return user.id();
        }catch (MalformedURLException e){
            throw new BotException(ExceptionCodeEnums.URI_FORMAT);
        }catch (IOException e){
            throw new BotUserNotFoundException(ExceptionCodeEnums.USER_NOT_FOUND);
        }
    }
}
