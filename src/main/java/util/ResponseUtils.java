package util;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseUtils {

    public static void responseWithJson(HttpExchange exchange, String json, int code) throws IOException {
        exchange.getResponseHeaders().add("content-type", "application/json");
        exchange.sendResponseHeaders(code, json.length());
        OutputStream output = exchange.getResponseBody();
        output.write(json.getBytes());
        output.flush();
        exchange.close();
    }

}
