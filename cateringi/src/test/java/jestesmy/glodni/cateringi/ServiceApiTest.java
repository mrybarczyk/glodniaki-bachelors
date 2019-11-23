package jestesmy.glodni.cateringi;

import jestesmy.glodni.cateringi.model.Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import io.restassured.RestAssured;
import io.restassured.response.Response;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { CateringiApplication.class }, webEnvironment
        = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ServiceApiTest {

    private static final String API_ROOT
            = "http://localhost:8080/api/services";

    private Service createRandomService() {
        Service service = new Service();
        service.setServiceName(randomAlphabetic(10));
        service.setDescription(randomAlphabetic(100));
        return service;
    }

    private String createServiceAsUri(Service service) {
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(service)
                .post(API_ROOT);
        return API_ROOT + "/" + response.jsonPath().get("serviceID");
    }

    @Test
    public void whenGetAllService_thenOK() {
        Response response = RestAssured.get(API_ROOT);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetServicesByTitle_thenOK() {
        Service service = createRandomService();
        createServiceAsUri(service);
        Response response = RestAssured.get(
                API_ROOT + "/name/" + service.getServiceName());

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertTrue(response.as(List.class)
                .size() > 0);
    }
    @Test
    public void whenGetCreatedServiceById_thenOK() {
        Service service = createRandomService();
        String location = createServiceAsUri(service);
        Response response = RestAssured.get(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals(service.getServiceName(), response.jsonPath()
                .get("serviceName"));
    }

    @Test
    public void whenGetNotExistServiceById_thenNotFound() {
        Response response = RestAssured.get(API_ROOT + "/" + randomNumeric(4));

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }

    @Test
    public void whenCreateNewService_thenCreated() {
        Service service = createRandomService();
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(service)
                .post(API_ROOT);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Test
    public void whenInvalidService_thenError() {
        Service service = createRandomService();
        service.setServiceName(null);
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(service)
                .post(API_ROOT);

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode());
    }

    @Test
    public void whenUpdateCreatedService_thenUpdated() {
        Service service = createRandomService();
        String location = createServiceAsUri(service);
        service.setServiceID(Integer.parseInt(location.split("api/services/")[1]));
        service.setDescription("newService");
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(service)
                .put(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        assertEquals("newService", response.jsonPath()
                .get("description"));
    }

    @Test
    public void whenDeleteCreatedService_thenOk() {
        Service service = createRandomService();
        String location = createServiceAsUri(service);
        Response response = RestAssured.delete(location);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());

        response = RestAssured.get(location);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode());
    }
}
