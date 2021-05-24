package DAO;

import model.AbstractDTO;
import model.Account;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Направляет запросы по счетам в БД через JDBC, реализует общий интерфейс DAO
 */

public class AccountDAO extends AbstractDAO{

    private final String getOne = "select * from ACCOUNTS where id = ?";
    private final String increaseBalance = "update ACCOUNTS set balance = ? where id = ?";

    @Override
    public Account getOneById(int accountID) throws SQLException {
        return executeQuery(getOne, statement -> {
            statement.setInt(1, accountID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Account account = buildAccount(resultSet);
                return account;
            }
            return null;
        });
    }


    @Override
    public int update(AbstractDTO dto) {
        int id = ((Account) dto).getID();
        double newBalance = ((Account) dto).getBalance();
        try {
            return executeQuery(increaseBalance, statement -> {
                statement.setDouble(1, newBalance);
                statement.setInt(2, id);
                return statement.executeUpdate();
            });
        } catch (SQLException e) {
            return 0;
        }

    /*    if (dto instanceof Balance) {

            System.out.println(newBalance);
            executeQuery(increaseBalance, statement -> {
                statement.setInt(1, id);
                statement.setDouble(2, newBalance);
                //statement.executeUpdate();
                System.out.println(statement.executeUpdate());
                return null;
            });
        }*/
    }

    private Account buildAccount(ResultSet resultSet) throws SQLException {
        return new Account(
                resultSet.getInt(1),
                resultSet.getInt(2),
                resultSet.getLong(3),
                resultSet.getDouble(4));
    }
}
