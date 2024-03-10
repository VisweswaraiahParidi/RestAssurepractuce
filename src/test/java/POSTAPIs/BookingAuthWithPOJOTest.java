package POSTAPIs;

import POJO.Credentials;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BookingAuthWithPOJOTest {
    @Test
    public void getBooingAuthToeknTest() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/";
        Credentials cred = new Credentials("admin", "password123");
        String token = given().log().all()
                .contentType(ContentType.JSON)
                .body(cred)
                .when()
                .post("/auth")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("token");

        System.out.println("Tokenuid is: " + token);
        Assert.assertNotNull(token);
    }
}
