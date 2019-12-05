package jestesmy.glodni.cateringi;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import jestesmy.glodni.cateringi.model.Client;
import jestesmy.glodni.cateringi.model.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { CateringiApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class OrderApiTest {
    private static final String API_ROOT = "http://localhost:8080/api/orders";

    private static final String API_ROOT_CLIENTS = "http://localhost:8080/api/clients";

    private Client createRandomClient() {
        Client client = new Client();
        client.setName(randomAlphabetic(10));
        client.setLastName(randomAlphabetic(15));
        client.setClientID(Integer.parseInt(randomNumeric(4)));
        return client;
    }

    private Order createRandomOrder(){
        Client client = createRandomClient();
        RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(client)
                .post(API_ROOT_CLIENTS);
        Order order = new Order();
        order.setOrderID(Integer.parseInt(randomNumeric(4)));
        order.setOrderDate(Timestamp.valueOf("2019-11-11 12:20:04"));
        order.setDeliveryDate(Timestamp.valueOf("2019-12-12 14:10:09"));
        order.setAddressID(Integer.parseInt(randomNumeric(4)));
        order.setClient(client);
        return order;
    }

    private String createOrderAsUri(Order order) {
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(order)
                .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath().get("orderID");
    }

    @Test
    public void whenGetAllOrders_thenOK(){
        Response response = RestAssured.get(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetOrdersByClientID_thenOK() {
        Order order = createRandomOrder();
        createOrderAsUri(order);
        Response response = RestAssured.get(API_ROOT + "/client/" + order.getClient().getClientID());
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class).size() > 0);
    }

    @Test
    public void whenGetCreatedOrderById_thenOK(){
        Order order = createRandomOrder();
        String location = createOrderAsUri(order);
        Response response = RestAssured.get(location);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetNotExistOrderById_thenNotFound() {
        Response response = RestAssured.get(API_ROOT + "/" + randomNumeric(4));
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void whenCreateNewOrder_thenCreated(){
        Order order = createRandomOrder();
        Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(order).post(API_ROOT);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void whenInvalidOrder_thenError(){
        Order order = createRandomOrder();
        order.setOrderDate(null);
        Response response = RestAssured.given().contentType(MediaType.APPLICATION_JSON_VALUE).body(order).post(API_ROOT);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedOrder_thenUpdated() {
        DateFormat df = DateFormat.getDateTimeInstance();
        df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Order order = createRandomOrder();
        String location = createOrderAsUri(order);
        order.setOrderID(Integer.parseInt(location.split("api/orders/")[1]));
        order.setDeliveryDate(Timestamp.valueOf("2019-07-10 15:30:07"));
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(order)
                .put(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(df.format(Timestamp.valueOf("2019-07-10 15:30:07")), response.jsonPath()
                .get("deliveryDate"));
    }

    @Test
    public void whenDeleteCreatedOrder_thenOk() {
        Order order  = createRandomOrder();
        String location = createOrderAsUri(order);
        Response response = RestAssured.delete(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }
}
