package AuthAPIs;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AuthAPITest {

    @BeforeTest
            public void allureSetup(){
        RestAssured.filters(new AllureRestAssured());
    }
    String accessToken;

    @Test
    public void jwtAuthwithJsonBody() {

        RestAssured.baseURI = "https://fakestoreapi.com";

        accessToken = given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        " \"username\": \"mor_2314\",\n" +
                        " \"password\": \"83r5^_\"\n" +
                        " }")
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .and()
                .extract()
                .path("token");

        System.out.println("Acesstoken : " + accessToken);
        String tokeneExtract[] = accessToken.split("\\.");
        System.out.println(tokeneExtract[0]);
        System.out.println(tokeneExtract[1]);
        System.out.println(tokeneExtract[2]);
    }

    @Test
    public void basicAuthTest() {

        RestAssured.baseURI = "https://the-internet.herokuapp.com/";

        String resposeBody = given()
                .auth().basic("admin", "admin")
                .when()
                .get("/basic_auth")
                .then()
                .statusCode(200)
                .and()
                .extract()
                .body().asString();

        System.out.println("resposeBody : " + resposeBody);
    }

    @Test
    public void preemtivebasicAuthTest() {

        RestAssured.baseURI = "https://the-internet.herokuapp.com/";

        String resposeBody = given()
                .auth().preemptive().basic("admin", "admin")
                .when()
                .get("/basic_auth")
                .then()
                .statusCode(200)
                .and()
                .extract()
                .body().asString();

        System.out.println("resposeBody : " + resposeBody);
    }

    @Test
    public void digetveebasicAuthTest() {

        RestAssured.baseURI = "https://the-internet.herokuapp.com/";

        String resposeBody = given()
                .auth().digest("admin", "admin")
                .when()
                .get("/basic_auth")
                .then()
                .statusCode(200)
                .and()
                .extract()
                .body().asString();

        System.out.println("resposeBody : " + resposeBody);
    }

    @Test
    public void apiKeyAuthTest() {

        RestAssured.baseURI = "http://api.weatherapi.com";

        String resposeBody = given()
                .queryParam("q", "Delhi")
                .queryParam("aqi", "no")
                .queryParam("key", "fc8f7748586e43e39d313213243001")
                .when().log().all()
                .get("/v1/current.json")
                .then()
                .statusCode(200)
                .and()
                .extract()
                .body().asString();

        System.out.println("resposeBody : " + resposeBody);
    }
}
