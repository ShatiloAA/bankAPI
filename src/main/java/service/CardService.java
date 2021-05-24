package service;

import DAO.CardDAO;
import model.Card;

import java.sql.SQLException;
import java.util.List;

/**
 * Сервис по операциям с картами,  направляет запросы в БД посредством CardDAO
 * Реализованный функционал:
 * 1) Выпуск новой карты по счету
 * 2) Просмотр списка карт
 */

public class CardService extends AbstractService {

    public CardService() {
        super(new CardDAO());
    }

    /**
     * Выпуск новой карты по счету, в аргументах принимается id счета, т.к. номер счета передавать небезопасно
     * @param accountID id счета
     * @throws SQLException
     */

    /**
     * Выпуск новой карты по счету, в аргументах принимается id счета, т.к. номер счета передавать небезопасно
     * @param card принимает Card DTO
     * @return возвращает кол-во измененных строк
     * @throws SQLException
     */
    public int postNewCard(Card card) throws SQLException {
        return  ((CardDAO) dao).create(card);
    }

    /**
     * Получение всех карт по id пользователя
     *
     * @param accountID id счета
     * @return List<Card>
     * @throws SQLException
     */

    public List<Card> getAllCardsByAccountID(int accountID) throws SQLException {
        return ((CardDAO) dao).getAllByAccountId(accountID);
    }

    /**
     * Получение карты по id карты
     * @param cardID id карты
     * @return объект Card
     * @throws SQLException
     */

    public Card getCardByID(int cardID) throws SQLException {
        return ((CardDAO) dao).getOneById(cardID);
    }

}
