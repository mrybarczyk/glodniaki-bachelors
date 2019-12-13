package jestesmy.glodni.cateringi;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.response.Response;
import jestesmy.glodni.cateringi.model.Client;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {CateringiApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ClientAPITest {
    private static final String API_ROOT = "http://localhost:8080/api/clients";

    private Client createRandomClient() {
        Client client = new Client();
        client.setName(randomAlphabetic(10));
        client.setLastName(randomAlphabetic(15));
        client.setClientID(Integer.parseInt(randomNumeric(4)));
        return client;
    }

    private String createClientAsUri(Client client) {
        Response response = RestAssured.given().auth().form("admin","admin", new FormAuthConfig("/login","username","password")).when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(client)
                .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath()
                .get("clientID");
    }

    @Test
    public void whenGetAllClients_thenOK() {
        Response response = RestAssured.given().auth().form("admin","admin", new FormAuthConfig("/login","username","password")).when().get(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetClientByLastName_thenOK() {
        Client client = createRandomClient();
        createClientAsUri(client);

        Response response = RestAssured.given().auth().form("admin","admin", new FormAuthConfig("/login","username","password")).when().get(API_ROOT+"/lastName/"+client.getLastName());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class)
                .size() > 0);
    }

    @Test
    public void whenGetCreatedClientById_thenOK() {
        Client client = createRandomClient();
        String uri = createClientAsUri(client);
        Response response = RestAssured.given().auth().form("admin","admin", new FormAuthConfig("/login","username","password")).when().get(uri);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(client.getLastName(), response.jsonPath()
                .get("lastName"));
    }

    @Test
    public void whenGetNotExistClientById_thenNotFound() {
        Response response = RestAssured.given().auth().form("admin","admin", new FormAuthConfig("/login","username","password")).when().get(API_ROOT+"/"+randomNumeric(4));
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatusCode());
    }

    // POST
    @Test
    public void whenCreateNewClient_thenCreated() {
        Client client = createRandomClient();
        Response response = RestAssured.given().auth().form("admin","admin", new FormAuthConfig("/login","username","password")).when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(client)
                .post(API_ROOT);
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void whenInvalidClient_thenError() {
        Client client = createRandomClient();
        client.setLastName(null);

        Response response = RestAssured.given().auth().form("admin","admin", new FormAuthConfig("/login","username","password")).when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(client)
                .post(API_ROOT);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedClient_thenUpdated() {
        Client client = createRandomClient();
        String uri = createClientAsUri(client);

        client.setClientID(Integer.parseInt(uri.split("api/clients/")[1]));
        client.setLastName("Kowalski");
        Response response = RestAssured.given().auth().form("admin","admin", new FormAuthConfig("/login","username","password")).when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(client)
                .put(uri);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.given().auth().form("admin","admin", new FormAuthConfig("/login","username","password")).when().get(uri);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("Kowalski", response.jsonPath()
                .get("lastName"));

    }

    @Test
    public void whenDeleteCreatedClient_thenOk() {
        Client client = createRandomClient();
        String uri = createClientAsUri(client);
        Response response = RestAssured.given().auth().form("admin","admin", new FormAuthConfig("/login","username","password")).when().delete(uri);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.given().auth().form("admin","admin", new FormAuthConfig("/login","username","password")).when().get(uri);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }
}