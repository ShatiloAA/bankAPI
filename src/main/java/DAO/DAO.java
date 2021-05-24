package DAO;

import model.AbstractDTO;
import util.PropertyManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface DAO {



    default <R> R executeQuery(String query, Executor<R> executor) throws SQLException {
        try (Connection connection = DriverManager.getConnection(PropertyManager.DB_URL)) {
            PreparedStatement ps = connection.prepareStatement(query);
            return executor.apply(ps);
        }
    }

    @FunctionalInterface
    interface Executor<R> {
        R apply(PreparedStatement statement) throws SQLException;
    }

}
