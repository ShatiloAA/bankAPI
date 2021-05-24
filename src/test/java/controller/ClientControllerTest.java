package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import model.Card;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import security.Authenticator;
import util.DBUtils;
import web.EchoHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.PropertyManager.AUTH_REALM;
import static util.PropertyManager.SERVER_PORT;

class ClientControllerTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private HttpServer server;

    @BeforeEach
    void setUp() throws IOException {
        server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(SERVER_PORT), 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.createContext("/", new EchoHandler()).setAuthenticator(new Authenticator(AUTH_REALM));
        server.setExecutor(null); // creates a default executor
        DBUtils dbUtils = new DBUtils();
        dbUtils.fill();
        server.start();
    }

    @AfterEach
    void stopServer() {
        server.stop(0);
    }

    @Test
    void handlePost405() throws IOException {
        int port = SERVER_PORT;
        URL url = new URL("http://localhost:" + port + "/users/test/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Authorization", "Basic YmVhbjpZbVZoYmpFME9EZz0=");
        assertEquals(405, connection.getResponseCode());
    }

    @Test
    void handlePost400WithBadAccountID() throws IOException {
        int port = SERVER_PORT;
        URL url = new URL("http://localhost:" + port + "/users/cards/3");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Basic YmVhbjpZbVZoYmpFME9EZz0=");
        assertEquals(400, connection.getResponseCode());
    }

    @Test
    void handlePost200WithGetAllCardForTestAccountID() throws IOException {
        int port = SERVER_PORT;
        URL url = new URL("http://localhost:" + port + "/users/cards/1");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Basic YmVhbjpZbVZoYmpFME9EZz0=");
        assertEquals(200, connection.getResponseCode());
    }

    @Test
    void handlePost400WithBadAccountIDForGetBalance() throws IOException {
        int port = SERVER_PORT;
        URL url = new URL("http://localhost:" + port + "/users/accounts/3");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Basic YmVhbjpZbVZoYmpFME9EZz0=");
        connection.setDoOutput(true);
        assertEquals(400, connection.getResponseCode());
    }

    @Test
    void handlePost200WithNewCard() throws IOException {
        int port = SERVER_PORT;
        URL url = new URL("http://localhost:" + port + "/users/accounts/2");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Basic YmVhbjpZbVZoYmpFME9EZz0=");
        connection.setDoOutput(true);
        Card card = new Card();
        String jsonRequest = mapper.writeValueAsString(card);
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(jsonRequest);
        out.flush();
        out.close();

        assertEquals(200, connection.getResponseCode());
    }
}