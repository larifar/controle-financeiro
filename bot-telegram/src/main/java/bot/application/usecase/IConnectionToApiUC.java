package bot.application.usecase;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public interface IConnectionToApiUC {
    InputStream get(URL api) throws IOException;
    void post(URL api, String json) throws IOException;
}
