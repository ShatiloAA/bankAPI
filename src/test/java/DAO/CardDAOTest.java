package DAO;

import model.Card;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardDAOTest {
    final static CardDAO dao = new CardDAO();
    final static Card CARD1 = new Card(1, 1000000000000000L, 1, true);
    final static Card CARD2 = new Card(2, 1000000000000001L, 1, true);
    final static List<Card> twoCards = Arrays.asList(CARD1, CARD2);

    @Test
    void getOneById() throws SQLException {
        Card cardFromDB = dao.getOneById(CARD1.getAccountID());
        assertEquals(CARD1, cardFromDB);
    }

    @Test
    void getOneByIdNegativeCase() throws SQLException {
        Card cardFromDB = dao.getOneById(4);
        assertNull(cardFromDB);
    }

    @Test
    void getAllByAccountId() throws SQLException {
        List<Card> cardsFromDB = dao.getAllByAccountId(1);
        assertEquals(twoCards, cardsFromDB);
    }

    @Test
    void getAllByAccountIdNegativeCase() throws SQLException {
        List<Card> cardsFromDB = dao.getAllByAccountId(4);
        assertNull(cardsFromDB);
    }
}