package util;

import DAO.DAO;
import org.apache.maven.model.Model;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static util.PropertyManager.DB_SCRIPT;


/**
 * Утильный класс для наполнения БД тестовыми данными
 */
public class DBUtils implements DAO {

    /**
     * Наполняет БД посредством запуска SQL скрипта
     */

    public void fill() {

        InputStream in = Model.class.getClassLoader().getResourceAsStream("initDB.sql");
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (in, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            executeQuery(textBuilder.toString(), ps -> {
                ps.executeUpdate();
                return null;
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
