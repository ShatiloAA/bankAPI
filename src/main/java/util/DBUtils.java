package util;

import DAO.DAO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static util.PropertyManager.DB_SCRIPT;

public class DBUtils implements DAO {


    public void fill() {

        String content = "";
        try (Stream<String> lines = Files.lines(Paths.get(DB_SCRIPT))) {
            content = lines.collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            executeQuery(content, ps -> {
                ps.executeUpdate();
                return null;
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
