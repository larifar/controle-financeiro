package bot.infra.implementations;

import bot.application.usecase.IConnectionToApiUC;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionToApiUCImpl implements IConnectionToApiUC {

    @Override
    public InputStream get(URL api) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) api.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        int code = connection.getResponseCode();
        if (code >= 200 && code < 300) {
            return connection.getInputStream();
        } else {
            throw new IOException("Erro na requisição GET: " + code);
        }
    }

    @Override
    public void post(URL api, String json) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) api.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);


        try (OutputStream os = connection.getOutputStream()) {
            os.write(json.getBytes("utf-8"));
        }

        int code = connection.getResponseCode();
        if (code < 200 || code > 300) {
            throw new IOException("Erro na requisição POST: " + code);
        }
    }
}
