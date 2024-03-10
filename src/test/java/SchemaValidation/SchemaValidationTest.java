package SchemaValidation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class SchemaValidationTest {

    @Test
    public void addSchemaUserTest() {
        RestAssured.baseURI = "https://gorest.co.in";
        given().log().all()
                .contentType(ContentType.JSON)
                .body(new File("C:\\UsersViswa\\eclipse-workspace\\RestAssuredPract2024\\src\\test\\resources\\resources\\BodyContenttoaddUser.json"))
                .header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794")
                .when().log().all()
                .post("/public/v2/users/")
                .then().log().all()
                .assertThat()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("createschemevalidator.json"));
    }

    @Test
    public void getSchemaUserTest() {
        RestAssured.baseURI = "https://gorest.co.in";
        given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794")
                .when().log().all()
                .get("/public/v2/users/")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("getuserschema.json"));


    }
}