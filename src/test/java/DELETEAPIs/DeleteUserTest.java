package DELETEAPIs;

import DELETEAPIs.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DeleteUserTest {
    //POST Create a new user
    public static String getRandonEmail() {
        return "Automation" + System.currentTimeMillis() + "@gmail.com";
    }

    //POST Create user with Lombok Builder Pattern -201
    @Test
    public void deleteUserTest() {

        RestAssured.baseURI = "https://gorest.co.in";

        User user = new User.UserBuilder()
                .name("Viswa")
                .email(getRandonEmail())
                .gender("male")
                .status("active")
                .build();

        Response response = given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794")
                .body(user)
                .when().log().all()
                .post("/public/v2/users");
        Integer userId = response.jsonPath().get("id");
        System.out.println("UserId: " + userId);

        response.prettyPrint();
//DELETE - selete User -204
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794")
                .when().log().all()
                .delete("/public/v2/users/" + userId)
                .then().log().all()
                .assertThat()
                .statusCode(204);

//GET -delete  user details -404
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794")
                .when().log().all()
                .get("/public/v2/users/" + userId)
                .then().log().all()
                .assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Resource not found"));


    }
}
