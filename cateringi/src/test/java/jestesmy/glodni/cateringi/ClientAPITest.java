package jestesmy.glodni.cateringi;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import jestesmy.glodni.cateringi.model.Client;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ClientAPITest {
    private static final String API_ROOT = "http://localhost:8080/api/clients";

    @Test
    public void whenGetAllClients_thenOK() {
        final io.restassured.response.Response response = RestAssured.get(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetClientByName_thenOK() {
        final Client client = createRandomClient();
        createClientAsUri(client);

        final io.restassured.response.Response response = RestAssured.get(API_ROOT + "/title/" + client.getName());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class)
                .size() > 0);
    }

    @Test
    public void whenGetCreatedClientById_thenOK() {
        final Client client = createRandomClient();
        final String location = createClientAsUri(client);

        final io.restassured.response.Response response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(client.getName(), response.jsonPath()
                .get("name"));
    }

    @Test
    public void whenGetNotExistClientById_thenNotFound() {
        final io.restassured.response.Response response = RestAssured.get(API_ROOT + "/" + randomNumeric(4));
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    // POST
    @Test
    public void whenCreateNewClient_thenCreated() {
        final Client client = createRandomClient();

        final io.restassured.response.Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(client)
                .post(API_ROOT);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void whenInvalidClient_thenError() {
        final Client client = createRandomClient();
        client.setLastName(null);

        final io.restassured.response.Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(client)
                .post(API_ROOT);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedClient_thenUpdated() {
        final Client client = createRandomClient();
        final String location = createClientAsUri(client);

        client.setClientID(Integer.parseInt(location.split("api/clients/")[1]));
        client.setLastName("Kowalski");
        io.restassured.response.Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(client)
                .put(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("Kowalski", response.jsonPath()
                .get("name"));

    }

    @Test
    public void whenDeleteCreatedClient_thenOk() {
        final Client client = createRandomClient();
        final String location = createClientAsUri(client);

        Response response = RestAssured.delete(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    private Client createRandomClient() {
        final Client client = new Client();
        client.setName(randomAlphabetic(10));
        client.setLastName(randomAlphabetic(15));
        client.setClientID(Integer.parseInt(randomNumeric(4)));
        return client;
    }

    private String createClientAsUri(Client client) {
        final io.restassured.response.Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(client)
                .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath()
                .get("id");
    }
}
