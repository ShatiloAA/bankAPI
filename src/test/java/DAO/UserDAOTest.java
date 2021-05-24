package DAO;

import model.Role;
import model.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    final static UserDAO dao = new UserDAO();
    final static User USER1 = new User(1, "bean", "YmVhbjE0ODg=","Mister Bean", Role.USER);
    final static User USER2 = new User(2, "hill", "aGlsbGhpbGxoaWxs","Benny Hill", Role.USER);

    @Test
    void getOneByIdPositiveCase() throws SQLException {
        User userFromDB = (User) dao.getOneById(USER1.getId());
        assertEquals(USER1, userFromDB);
    }

    @Test
    void getOneByIdNegativeCase() throws SQLException {
        User userFromDB = (User) dao.getOneById(4);
        assertNull(userFromDB);
    }

    @Test
    void getOneByLoginPositiveCase() throws SQLException {
        User userFromDB = (User) dao.getOneByLogin(USER2.getLogin());
        assertEquals(USER2, userFromDB);
    }

    @Test
    void getOneByLoginNegativeCase() throws SQLException {
        User userFromDB = (User) dao.getOneByLogin("badLogin");
        assertNull(userFromDB);
    }
}