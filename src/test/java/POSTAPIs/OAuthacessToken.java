package POSTAPIs;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class OAuthacessToken {
    String accessToken;

    @BeforeMethod
    public void getAccessToken() {
        // POST to get the token
        RestAssured.baseURI = "https://test.api.amadeus.com";

        accessToken = given()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("grant_type", "client_credentials")
                .formParam("client_id", "1mdRtgatLLeVQsMTIPmlWWGPDwELZZAC")
                .formParam("client_secret", "zQlDEqqe4DvMRl7n")
                .when()
                .post("/v1/security/oauth2/token")
                .then()
                .assertThat()
                .statusCode(200)
                .extract().path("access_token");

        System.out.println("Access Token: " + accessToken);

    }

    @Test
    public void getFlightInfoTest() {

        //GET the flight information
        Response response = given().log().all()
                .header("Authorization", "Bearer " + accessToken)
                .queryParam("origin", "PAR")
                .queryParam("maxPrice", 200)
                .when()
                .get("/v1/shopping/flight-destinations")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .response();

        JsonPath js = response.jsonPath();
        String type = js.get("data[0].type");
        System.out.println(type);


    }
}
