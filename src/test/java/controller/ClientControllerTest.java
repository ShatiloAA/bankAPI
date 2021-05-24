package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
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
import java.nio.charset.StandardCharsets;
import java.util.Base64;

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
        URL url = new URL("http://localhost:" + SERVER_PORT + "/users/test/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Authorization", "Basic YmVhbjpZbVZoYmpFME9EZz0=");
        assertEquals(405, connection.getResponseCode());
    }

    @Test
    void handlePost404WithBadAccountID() throws IOException {
        URL url = new URL("http://localhost:" + SERVER_PORT + "/users/account/4");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Basic YmVhbjpZbVZoYmpFME9EZz0=");
        assertEquals(404, connection.getResponseCode());
    }

    @Test
    void handlePost200WithGetAllCardForTestAccountID() throws IOException {
        URL url = new URL("http://localhost:" + SERVER_PORT + "/users/cards/1");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Basic YmVhbjpZbVZoYmpFME9EZz0=");
        assertEquals(200, connection.getResponseCode());
    }

    @Test
    void handlePost400WithBadAccountIDForGetBalance() throws IOException {
        URL url = new URL("http://localhost:" + SERVER_PORT + "/users/accounts/4");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Basic YmVhbjpZbVZoYmpFME9EZz0=");
        assertEquals(400, connection.getResponseCode());
    }

    @Test
    void handlePost200WithNewCard() throws IOException {
        URL url = new URL("http://localhost:" + SERVER_PORT + "/users/cards/");
        String base64 = Base64.getEncoder().encodeToString("hill:aGlsbGhpbGxoaWxs".getBytes(StandardCharsets.UTF_8));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Basic " + base64);
        connection.setDoOutput(true);
        String jsonRequest = "{\n" +
                "  \"accountID\": \"3\"\n" +
                "}";
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(jsonRequest);
        out.flush();
        out.close();
        assertEquals(200, connection.getResponseCode());
    }

    @Test
    void handlePost400WithNewCard() throws IOException {
        URL url = new URL("http://localhost:" + SERVER_PORT + "/users/cards/");
        String base64 = Base64.getEncoder().encodeToString("hill:aGlsbGhpbGxoaWxs".getBytes(StandardCharsets.UTF_8));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Basic " + base64);
        connection.setDoOutput(true);
        String jsonRequest = "{\n" +
                "  \"accountID\": \"4\"\n" +
                "}";
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(jsonRequest);
        out.flush();
        out.close();
        assertEquals(400, connection.getResponseCode());
    }

    @Test
    void handlePost200WithIncreaseBalance() throws IOException {
        URL url = new URL("http://localhost:" + SERVER_PORT + "/users/accounts/");
        String base64 = Base64.getEncoder().encodeToString("hill:aGlsbGhpbGxoaWxs".getBytes(StandardCharsets.UTF_8));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Basic " + base64);
        connection.setDoOutput(true);
        String jsonRequest = "{\n" +
                "  \"accountID\":\"3\",\"balance\":\"100\"\n" +
                "}";
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(jsonRequest);
        out.flush();
        out.close();
        assertEquals(200, connection.getResponseCode());
    }

    @Test
    void handlePost400WithIncreaseBalance() throws IOException {
        URL url = new URL("http://localhost:" + SERVER_PORT + "/users/accounts/");
        String base64 = Base64.getEncoder().encodeToString("hill:aGlsbGhpbGxoaWxs".getBytes(StandardCharsets.UTF_8));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Basic " + base64);
        connection.setDoOutput(true);
        String jsonRequest = "{\n" +
                "  \"accountID\":\"3\",\"balance\":\"-1\"\n" +
                "}";
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(jsonRequest);
        out.flush();
        out.close();
        assertEquals(400, connection.getResponseCode());
    }


}