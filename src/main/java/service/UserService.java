package service;

import DAO.UserDAO;
import model.User;
import security.PasswordEncryptor;

import java.sql.SQLException;
import java.util.Objects;

/**
 * Сервис реализует работу с пользователем, используется для аутентификации.
 */

public class UserService extends AbstractService {

    public UserService() {
        super(new UserDAO());
    }


    public boolean authentication(String login, String password) {
        boolean result = false;
        User user = getUserByLogin(login);
        if (Objects.nonNull(user)) {
            result = PasswordEncryptor.checkPassword(password, user.getPassword());
        }
        return result;
    }

    /**
     * Метод возвращает пользователя по логину.
     *
     * @param login принимает логин пользователя
     * @return возвращает объект User
     * @throws SQLException
     */
    public User getUserByLogin(String login) {
        User user = null;
        try {
            user = ((UserDAO) dao).getOneByLogin(login);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


}
