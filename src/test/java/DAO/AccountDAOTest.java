package DAO;

import model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DBUtils;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AccountDAOTest {
    final static Account ACCOUNT1 =  new Account(1, 1, 123400000000L, 148.84);
    final static Account ACCOUNT2 =  new Account(1, 1, 123400000000L, 150.00);
    final static AccountDAO accountDAO = new AccountDAO();

    @BeforeEach
    void initDB() {
        new DBUtils().fill();
    }

    @Test
    void getOneByIdPositiveCase() throws SQLException {
        Account accountFromDB = accountDAO.getOneById(ACCOUNT1.getID());
        assertEquals(ACCOUNT1, accountFromDB);
    }

    @Test
    void getOneByIdNegativeCase() throws SQLException {
        Account accountFromDB = accountDAO.getOneById(2);
        assertNotEquals(ACCOUNT1, accountFromDB);
    }

    @Test
    void updatePositiveCase() {
        assertEquals(1, accountDAO.update(ACCOUNT2));
    }

    /*@Test
    void updateNegativeCase() {
        assertEquals(0, accountDAO.update(ACCOUNT1));
    }*/
}