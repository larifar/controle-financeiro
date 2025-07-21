package bot.infra.implementations;

import bot.application.usecase.IConnectionToApiUC;
import bot.domain.exception.BotException;
import bot.domain.exception.BotUserNotFoundException;
import bot.domain.exception.ExceptionCodeEnums;
import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ConnectionToApiUCImpl implements IConnectionToApiUC {
    private final OkHttpClient client;

    public ConnectionToApiUCImpl(OkHttpClient client) {
        this.client = client;
    }


    @Override
    public InputStream get(URL api) throws IOException {
        Request request = new Request.Builder()
                .url(api)
                .get()
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            assert response.body() != null;
            return response.body().byteStream();
        } else {
            String error = response.body() != null ? response.body().string() : "";
            if (error.contains("USER-01")){
                throw new BotUserNotFoundException(ExceptionCodeEnums.USER_NOT_FOUND);
            }
            throw new BotException("Erro na requisição GET: " + error, String.valueOf(response.code()));
        }
    }

    @Override
    public void post(URL api, String json) throws IOException {
        RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(api)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            String error = response.body() != null ? response.body().string() : "";
            throw new BotException("Erro na requisição POST: " + error, String.valueOf(response.code()));
        }
    }
}
