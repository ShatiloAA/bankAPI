package web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.ClientController;
import controller.Controller;

import java.io.IOException;
import java.sql.SQLException;

import static util.URIUtils.getResource;

public class EchoHandler implements HttpHandler {

    /**
     * Импелементация HttpHandler, в зависимости от маршрута ресурса вызывает соответствующий контроллер.
     * Контроллеру передается в конструктор HttpExchange для дальнейшего парсинга и работы с ним.
     *
     * @param exchange принимает на вход HttpExchange
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String responseURI = exchange.getRequestURI().toString();
        String resource = getResource(responseURI, 0);
        Controller controller;
        switch (resource) {
            case "users":
                controller = new ClientController(exchange);
                try {
                    controller.parseRequests();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
                //not released
            case "contractors":
                //not released
            case "employees":
            default:
                exchange.sendResponseHeaders(404, -1);
        }
    }
}
