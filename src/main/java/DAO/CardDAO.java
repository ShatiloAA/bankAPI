package DAO;

import model.AbstractDTO;
import model.Card;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Направляет запросы по картам в БД через JDBC, реализует общий интерфейс DAO
 */

public class CardDAO extends AbstractDAO {

    private final String getCardsByAccountID = "select c.id, c.number, account_id, is_active from cards c " +
            "join accounts a on c.account_id = a.id where a.id =?";
    private final String getCardByID = "select * from cards where id = ?";
    private final String newCard = "insert into cards (account_id, is_active) " +
            "values (?, true)";

    /**
     * Поиск карты по cardID
     * @param ID  принимает ID по которому осуществляется поиск в БД
     * @return DTO CARD
     * @throws SQLException
     */
    @Override
    public Card getOneById(int ID) throws SQLException {
        return executeQuery(getCardByID, statement -> {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                return buildCard(resultSet);
            }
            return null;
        });
    }

    /**
     * Возввращает все карты по accountID
     * @param accountID
     * @return List<Card>
     * @throws SQLException
     */
    public List<Card> getAllByAccountId(int accountID) throws SQLException {
        return executeQuery(getCardsByAccountID, statement -> {
            statement.setInt(1, accountID);
            List<Card> cards = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cards.add(buildCard(resultSet));
            }
            return cards.size() > 0 ? cards : null;
        });
    }

    /**
     * Создает запись в БД из переданного DTO
     * @param dto принимает реализацию dto
     * @return количество измененных строк
     */

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

    /**
     * Собирает объект из полученного resultSet
     * @param resultSet
     * @return объект Card
     * @throws SQLException
     */
    private Card buildCard(ResultSet resultSet) throws SQLException {
        return new Card(
                resultSet.getInt(1),
                resultSet.getLong(2),
                resultSet.getInt(3),
                resultSet.getBoolean(4));
    }

}
