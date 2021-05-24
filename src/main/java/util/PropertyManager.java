package util;

import org.apache.maven.model.Model;

import java.io.FileReader;
import java.io.InputStream;
import java.util.Properties;

/**
 * Класс загрузчик configuration.properties с константами для удобства
 */

public class PropertyManager {
    private static Properties properties = new Properties();
    public static final String DB_URL = getProperties().getProperty("db.url");
    public static final String DB_DRIVER = getProperties().getProperty("db.driver");
    public static final String DB_SCRIPT = getProperties().getProperty("db.script");
    public static final String AUTH_REALM = getProperties().getProperty("auth.realm");
    public static final int SERVER_PORT = Integer.parseInt(getProperties().getProperty("port"));


    /**
     * Возвращает загруженные данные из файла для дальнейшего чтения
     * @return
     */
    private static Properties getProperties()  {
        try {
            InputStream in = Model.class.getClassLoader().getResourceAsStream("configuration.properties");
            properties.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }
}
