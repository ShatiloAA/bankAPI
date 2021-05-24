import com.sun.net.httpserver.HttpServer;
import web.Server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        Server.startServer();
    }
}
