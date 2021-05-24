package util;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 *  Утильный класс для работы с URI
 */
public class URIUtils {

    public final static String NOTFOUND = " not found";

    /**
     * Парсит URI в массив строк
     * @param uri
     * @return String[]
     */
    private static String[] parseURI(String uri) {
        return uri.substring(1).split("/");
    }

    /**
     * Достает из массива URI нужный ресурс
     * @param uri
     * @param index в массиве
     * @return String
     */

    public static String getResource(String uri, int index) {
        return parseURI(uri)[index];
    }

    /**
     * Возвращает ID при длинне запроса равной 3
     * @param URI
     * @return -1 если URI длинее, иначе вернет id
     */
    public static int getId(String URI) {
        if (URIUtils.sizePathValuesArray(URI) == 3) {
            return Integer.parseInt(URIUtils.getResource(URI, 2));
        }
        return -1;
    }

    /**
     * Проверяет ID на валидность, если он невалидный формирует ответ с json и кодом 404
     * @param URI
     * @param exchange
     * @return ID
     * @throws IOException
     */
    public static int getCheckedId(String URI, HttpExchange exchange) throws IOException {
        int id = getId(URI);
        if (id <= 0) {
            util.ResponseUtils.responseWithJson(exchange, "id invalid", 404);
            return id;
        } else {
            return Integer.parseInt(URIUtils.getResource(URI, 2));
        }
    }

    /**
     * Возвращает размер массива распарсенного из URI
     * @param URI
     * @return размер массива
     */
    public static int sizePathValuesArray(String URI) {
        return parseURI(URI).length;
    }


}
