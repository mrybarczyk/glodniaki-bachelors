package jestesmy.glodni.cateringi;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.response.Response;
import jestesmy.glodni.cateringi.domain.model.Company;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {CateringiApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CompaniesAPITest {

    private static final String API_ROOT="http://localhost:8080/api/companies";

    private Company createRandomCompany() {
        Company company = new Company();
        company.setName(randomAlphabetic(15));
        company.setNip(randomAlphanumeric(10));
        company.setRegon(randomAlphanumeric(9));
        company.setWebsiteAddress("https://"+randomAlphabetic(8));
        company.setCompanyID(Integer.parseInt(randomNumeric(4)));
        return company;
    }

    private String createCompanyAsUri(Company company) {
        Response response = RestAssured.given().auth().form("admin","admin", new FormAuthConfig("/login","username","password")).when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(company)
                .post(API_ROOT);
        return API_ROOT+"/"+response.jsonPath().get("companyID");
    }

    @Test
    public void whenGetAllCompanies_thenOK() {
        Response response = RestAssured.given().auth().form("admin","admin", new FormAuthConfig("/login","username","password")).when().get(API_ROOT);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @Test
    public void whenGetCompanyByName_thenOK(){
        Company company = createRandomCompany();
        createCompanyAsUri(company);
        Response response = RestAssured.given().auth().form("admin","admin", new FormAuthConfig("/login","username","password")).when().get(API_ROOT+"/name/"+company.getName());

        assertEquals(HttpStatus.OK.value(),response.getStatusCode());
        assertTrue(response.as(List.class).size()>0);
    }

    @Test
    public void whenGetCreatedCompanyById_thenOK() {
        Company company = createRandomCompany();
        String uri = createCompanyAsUri(company);
        Response response = RestAssured.given().auth().form("admin","admin", new FormAuthConfig("/login","username","password")).when().get(uri);

        assertEquals(HttpStatus.OK.value(),response.getStatusCode());
        assertEquals(company.getName(), response.jsonPath().get("name"));
    }

    @Test
    public void whenGetNotExistCompanyById_thenNotFound() {
        Response response = RestAssured.given().auth().form("admin","admin", new FormAuthConfig("/login","username","password")).when()
                .get(API_ROOT+"/"+randomNumeric(4));

        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatusCode());
    }

    @Test
    public void whenCreateNewCompany_thenCreated() {
        Company company = createRandomCompany();
        Response response = RestAssured.given().auth().form("admin","admin", new FormAuthConfig("/login","username","password")).when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(company)
                .post(API_ROOT);

        assertEquals(HttpStatus.CREATED.value(),response.getStatusCode());
    }

    @Test
    public void whenInvalidCompany_thenError() {
        Company company = createRandomCompany();
        company.setRegon(null);
        Response response = RestAssured.given().auth().form("admin","admin", new FormAuthConfig("/login","username","password")).when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(company)
                .post(API_ROOT);

        assertEquals(HttpStatus.BAD_REQUEST.value(),response.getStatusCode());

    }

    @Test
    public void whenUpdateCreatedCompany_thenUpdated() {
        Company company = createRandomCompany();
        String uri = createCompanyAsUri(company);
        company.setCompanyID(Integer.parseInt(uri.split("api/companies/")[1]));
        company.setName("Super nazwa!");
        Response response = RestAssured.given().auth().form("admin","admin", new FormAuthConfig("/login","username","password")).when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(company)
                .put(uri);

        assertEquals(HttpStatus.OK.value(),response.getStatusCode());

        response = RestAssured.given().auth().form("admin","admin", new FormAuthConfig("/login","username","password")).when().get(uri);

        assertEquals(HttpStatus.OK.value(),response.getStatusCode());
        assertEquals("Super nazwa!",response.jsonPath().get("name"));
    }

    @Test
    public void whenDeleteCreatedCompany_thenOk() {
        Company company = createRandomCompany();
        String uri = createCompanyAsUri(company);
        Response response = RestAssured.given().auth().form("admin","admin", new FormAuthConfig("/login","username","password")).when().delete(uri);

        assertEquals(HttpStatus.OK.value(),response.getStatusCode());

        response = RestAssured.given().auth().form("admin","admin", new FormAuthConfig("/login","username","password")).when().get(uri);
        assertEquals(HttpStatus.NOT_FOUND.value(),response.getStatusCode());
    }

}
