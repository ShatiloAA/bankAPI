package controller;

import com.sun.net.httpserver.HttpExchange;
import model.Account;
import model.Balance;
import model.Card;
import service.AccountService;
import service.CardService;
import util.URIUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * Клиентский контроллер расширяющий AbstractController.
 * Содержит в себе реализацию метода parseRequests().
 * Реализованный функционал:
 * 1) Выпуск новой карты по счету  +
 * 2) Просмотр списка карт +
 * 3) Внесение средств +
 * 4) Проверка баланса +
 */

public class ClientController extends AbstractController {

    {
        services.put("cards", new CardService());
        services.put("accounts", new AccountService());
    }

    public ClientController(HttpExchange exchange) {
        super(exchange);
    }


    /**
     * В зависимости от endpoint'а, типа и тела запросов вызывает необходимые сервисы.
     * Основной функционал заключается в парсинге пришедшего запроса, вызове нужного в конкретном случае сервиса
     * и в итоге добавления в тело JSON с результатом работы сервиса. Результат может быть, как положительный
     * с кодом ответа 200, так и отрицательный, о чем будет сообщено в теле ответа с кодом ошибки 400.
     * Если тип запроса некорректен в заголовок будет добавлен 405 код ошибки.
     * Пробрасывает следующие исключения:
     *
     * @throws IOException
     * @throws SQLException
     */

    public void parseRequests() throws IOException, SQLException {
        String URI = exchange.getRequestURI().toString();
        String typeOfRequest = exchange.getRequestMethod();
        String resource = URIUtils.getResource(URI, 1);
        String jsonResponse = resource + URIUtils.NOTFOUND;
        //int id = -1;
        Card card;
        Account account;
        Balance balance;

        switch (typeOfRequest) {
            case "GET":
                //get all cards by account
                if ("cards".equals(resource)) {
                    id = util.URIUtils.getCheckedId(URI, exchange);
                    CardService cardService = (CardService) getRequiredService(resource);
                    List<Card> cards = cardService.getAllCardsByAccountID(id);
                    if (cards.isEmpty()) {
                        util.ResponseUtils.responseWithJson(exchange, jsonResponse, 400);
                    } else {
                        jsonResponse = objectMapper.writeValueAsString(cards);
                        util.ResponseUtils.responseWithJson(exchange, jsonResponse, 200);
                    }
                }   //get balance
                else if ("accounts".equals(resource)) {
                    AccountService accountService = (AccountService) getRequiredService(resource);
                    balance = accountService.getBalance(id);
                    if (Objects.isNull(balance)) {
                        util.ResponseUtils.responseWithJson(exchange, jsonResponse, 400);
                    } else {
                        jsonResponse = objectMapper.writeValueAsString(balance);
                        util.ResponseUtils.responseWithJson(exchange, jsonResponse, 200);
                    }

                } else {
                    util.ResponseUtils.responseWithJson(exchange, jsonResponse, 404);
                }
                break;

            case "POST":

                //new card
                if ("cards".equals(resource)) {
                    CardService cardService = (CardService) getRequiredService(resource);
                    card = objectMapper.readValue(exchange.getRequestBody(), Card.class);
                    if (cardService.postNewCard(card) <= 0) {
                        util.ResponseUtils.responseWithJson(exchange, "Сard not created!", 400);
                    } else {
                        util.ResponseUtils.responseWithJson(exchange, "Congratulations! Card is create!", 200);
                    }

                }   //increaseBalance
                else if ("accounts".equals(resource)) {
                    balance = objectMapper.readValue(exchange.getRequestBody(), Balance.class);

                    if (balance.getBalance() > 0) {
                        AccountService accountService = (AccountService) getRequiredService(resource);
                        balance = accountService.increaseBalance(balance.getAccountID(), balance.getBalance());
                        if (Objects.isNull(balance)) {
                            util.ResponseUtils.responseWithJson(exchange, "Balance not increase! " +
                                    jsonResponse, 400);
                        } else {
                            jsonResponse = objectMapper.writeValueAsString(balance);
                            util.ResponseUtils.responseWithJson(exchange, jsonResponse, 200);
                        }
                    } else {
                        util.ResponseUtils.responseWithJson(exchange, "Balance not increase! " +
                                "The value is less than or equal to zero!", 400);
                    }
                }

                break;
            default:
                exchange.sendResponseHeaders(405, -1);
        }

    }


}
