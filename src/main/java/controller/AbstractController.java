package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import model.AbstractDTO;
import service.AbstractService;
import service.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static util.URIUtils.getId;

/**
 * Асбтрактная реализация интерфейса Controller с набором необходимым полей и констуруктором принмающим в качестве
 * аргумента HttpExchange.
 */


public abstract class AbstractController implements Controller {
    public Map<String, Service> services;
    protected HttpExchange exchange;
    protected ObjectMapper objectMapper = new ObjectMapper();
    protected int id;
    protected AbstractDTO dto;

    public AbstractController(HttpExchange exchange) {
        this.exchange = exchange;
        this.id = getId(exchange.getRequestURI().toString());
        this.services = new HashMap<>();
    }

    /**
     * Возвращает запрашиваемый сервис в зависимости от ресурса, если такового нет то Null
     * @param resource
     * @return запрашиваемый сервис в зависимости от ресурса, если такового нет то Null
     * @throws IOException
     */
    public Service getRequiredService(String resource) throws IOException {
        switch (resource) {
            case "cards":
            case "accounts":
            case "users":
                return services.get(resource);
            default:
                util.ResponseUtils.responseWithJson(exchange, "resource not find", 404);
                return null;
        }
    }

}
