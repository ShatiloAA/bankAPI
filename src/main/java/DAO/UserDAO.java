package DAO;

import model.AbstractDTO;
import model.Role;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Направляет запросы по пользователям в БД через JDBC, реализует общий интерфейс DAO
 */

public class UserDAO extends AbstractDAO {

    private final String getByID = "select * from users where id = ?";
    private final String getByLogin = "select * from users where login = ?";

    @Override
    public AbstractDTO getOneById(int id) throws SQLException {
        return executeQuery(getByID, statement -> {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return buildUser(resultSet);
            }
            return null;
        });
    }


    public User getOneByLogin(String login) throws SQLException {
        return executeQuery(getByLogin, statement -> {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return buildUser(resultSet);
            }
           return null;
        });
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                Enum.valueOf(Role.class, resultSet.getString(5)));
    }
}
