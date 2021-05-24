package DAO;

import model.AbstractDTO;
import model.Card;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Направляет запросы по картам в БД через JDBC, реализует общий интерфейс DAO
 */

public class CardDAO extends AbstractDAO {

    private final String getAll = "select * from cards";
    private final String getCardsByAccountID = "select c.id, c.number, account_id, is_active from cards c " +
            "join accounts a on c.account_id = a.id where a.id =?";
    private final String getCardByID = "select * from cards where id = ?";
    private final String newCard = "insert into cards (account_id, is_active) " +
            "values (?, true)";
    //private final String activateCardSql = "update card set active = true where id = ?";

    @Override
    public Card getOneById(int ID) throws SQLException {
        return executeQuery(getCardByID, statement -> {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            Card card = buildCard(resultSet);
            return card;
        });
    }

    public List<Card> getAllByAccountId(int accountID) throws SQLException {
        return executeQuery(getCardsByAccountID, statement -> {
            statement.setInt(1, accountID);
            List<Card> cards = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cards.add(buildCard(resultSet));
            }
            return cards;
        });
    }

    @Override
    public int create(AbstractDTO dto){
        Card card = (Card) dto;
        int countExecuteUpdate = 0;
        try {
            return executeQuery(newCard, statement -> {
                statement.setInt(1, card.getAccountID());
                return statement.executeUpdate();
            });
        } catch (SQLException e) {
            return countExecuteUpdate;
        }
    }

    private Card buildCard(ResultSet resultSet) throws SQLException {
        return new Card(
                resultSet.getInt(1),
                resultSet.getLong(2),
                resultSet.getInt(3),
                resultSet.getBoolean(4));
    }

}
