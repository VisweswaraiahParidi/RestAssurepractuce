package POSTAPIs;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class BookingAuthTest {

    @Test
    public void getBooingAuthToeknTest() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/";

        String token = given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"username\" : \"admin\",\n" +
                        "    \"password\" : \"password123\"\n" +
                        "}")
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

    @Test
    public void getBooingAuthToekn_with_Json_File_Test() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/";

        String token = given()
                .contentType(ContentType.JSON)
                .body(new File("C:\\Selenium Projects\\APIGradle\\src\\test\\resources\\BasicAuth.json"))
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

    @Test
    public void getBooingAuthToekn_Post_with_bosyandwith_Json_File_Test() {
        RestAssured.baseURI = "https://gorest.co.in";

        int userId = given().log().all()
                .contentType(ContentType.JSON)
                .body(new File("C:\\Selenium Projects\\APIGradle\\src\\test\\resources\\BodyContenttoaddUser.json"))
                .header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794")
                .when()
                .post("/public/v2/users/")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");

        System.out.println("user id :" + userId);
      Response response =  given().log().all()
              .header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794")
                .when()
                .get("/public/v2/users/" + userId)
                .then()
                .assertThat()
                .statusCode(404)
                .extract().response();

        JsonPath js = response.jsonPath();
        String messages = js.get("message");


        System.out.println("Tokenuid is: " +userId);
        System.out.println(messages);
  //      Assert.assertNotNull(message);

    }
}
