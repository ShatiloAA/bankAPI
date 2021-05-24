package web;

import com.sun.net.httpserver.HttpServer;
import security.Authenticator;
import util.DBUtils;

import java.io.IOException;
import java.net.InetSocketAddress;

import static util.PropertyManager.AUTH_REALM;
import static util.PropertyManager.SERVER_PORT;

public class Server {

    /**
     * Метод стартует API.
     * Создается сервер, затем контекст.
     * Контексту передатся "хэдлер" запросов и добавляется авторизация.
     * Авторизация принимает логин и пароль в виде String, но т.к. пароль хранится зашифрованным при помощи Base64,
     * то и передается он уже в зашифрованном виде.
     * Задается "дефолтный" executor и старует сервер.
     * Приложение не падает по Exception при обработке запросов, а перехватывает их, при нештатной ситуации в консоль
     * будет выведен стэк трейс. В противном случае неверный запрос (неверный URI), как и добавление заголовка
     * при нем (неверном URI) с кодом и телом проблемы может привести к падению приложения, что неадекватно.
     * При некорректной работе API просьба обращаться к автору AAShatilo@sberbank.ru
     *
     * @throws IOException
     */
    public static void startServer() {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(SERVER_PORT), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.createContext("/", new EchoHandler()).setAuthenticator(new Authenticator(AUTH_REALM));
        server.setExecutor(null); // creates a default executor
        DBUtils dbUtils = new DBUtils();
        dbUtils.fill();
        server.start();
    }


}
