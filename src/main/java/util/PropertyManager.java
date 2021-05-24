package util;

import java.io.FileReader;
import java.util.Properties;

public class PropertyManager {
    private static Properties properties = new Properties();
    public static final String DB_URL = getProperties().getProperty("db.url");
    public static final String DB_DRIVER = getProperties().getProperty("db.driver");
    public static final String DB_SCRIPT = getProperties().getProperty("db.script");
    public static final String AUTH_REALM = getProperties().getProperty("auth.realm");
    public static final int SERVER_PORT = Integer.parseInt(getProperties().getProperty("port"));


    private static Properties getProperties()  {
        try {
            properties.load(new FileReader("src/main/resources/configuration.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }
}
