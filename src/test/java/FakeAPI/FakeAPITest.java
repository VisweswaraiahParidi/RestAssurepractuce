package FakeAPI;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class FakeAPITest {
    @Test
    public void getProductsTest() {
        RestAssured.baseURI = "https://fakestoreapi.com";
        given().log().all()
                .when().log().all()
                .get("/products")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .header("Connection", "keep-alive")
                .and()
                .body("$.size()", equalTo(20))
                .body("id", is(notNullValue()))
                .and()
                .body("title", hasItem("Mens Cotton Jacket"));

        String connection = "keep-alive";
        Assert.assertEquals(connection, "keep-alive");
    }
}
