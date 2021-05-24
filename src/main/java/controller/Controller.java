package controller;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Интерфейс контроллера с методом parseRequests()
 */

public interface Controller {

    /**
     *
     * @throws IOException
     * @throws SQLException
     */
    void parseRequests() throws IOException, SQLException;

}
