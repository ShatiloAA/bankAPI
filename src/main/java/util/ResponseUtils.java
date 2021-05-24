package util;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Утильный класс для ответов с Json
 */

public class ResponseUtils {

    /**
     * Добавляет в заголовок код и Json в тело ответов
     * @param exchange принимает HttpExchange контекста
     * @param json String с Json
     * @param code ответа
     * @throws IOException
     */
    public static void responseWithJson(HttpExchange exchange, String json, int code) throws IOException {
        exchange.getResponseHeaders().add("content-type", "application/json");
        exchange.sendResponseHeaders(code, json.length());
        OutputStream output = exchange.getResponseBody();
        output.write(json.getBytes());
        output.flush();
        exchange.close();
    }

}
