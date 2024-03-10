package POSTAPIs;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;


public class CreateUserTest {

    @Test
    public void addUserTest() {
        RestAssured.baseURI = "https://gorest.co.in";
        int userId = given().log().all()
                .contentType(ContentType.JSON)
                .body(new File("C:\\Selenium Projects\\APIGradle\\src\\test\\resources\\BodyContenttoaddUser.json"))
                .header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794")
                .when()
                .post("/public/v2/users/")
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("id");


        System.out.println("user id :" + userId);
        Response response = given().log().all()
                .header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794")
                .when()
                .get("/public/v2/users/" + userId)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response();

        JsonPath js = response.jsonPath();
        String messages = js.get("message");


        System.out.println("Tokenuid is: " + userId);
        System.out.println(messages);
    }
}
