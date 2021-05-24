package util;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.URI;

public class URIUtils {

    public final static String NOTFOUND = " not found";

    private static String[] parseURI (String uri) {
        return uri.substring(1).split("/");
    }

    public static String getResource(String uri, int index) {
        return parseURI(uri)[index];
    }

    public static int getId(String URI) {
        if (URIUtils.sizePathValuesArray(URI) == 3) {
            return Integer.parseInt(URIUtils.getResource(URI, 2));
        }
        return -1;
    }

     public static int getCheckedId(String URI, HttpExchange exchange) throws IOException {
        int id = getId(URI);
        if (id <= 0) {
            util.ResponseUtils.responseWithJson(exchange, "id invalid", 404);
            return id;
        } else {
            return Integer.parseInt(URIUtils.getResource(URI, 2));
        }
    }


    public static int sizePathValuesArray(String uri)  {
        return parseURI(uri).length;
    }


}
