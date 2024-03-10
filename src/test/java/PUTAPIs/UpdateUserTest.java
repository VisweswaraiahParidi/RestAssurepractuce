package PUTAPIs;

import UserAPI.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UpdateUserTest {

    public static String getRandomEmail() {
        return "Automation" + System.currentTimeMillis() + "@gmail.com";
    }

    //1.POST - Create new User
    @Test
    public void updateUserTest() {
        RestAssured.baseURI = "https://gorest.co.in";
        User user = new User("viswa", getRandomEmail(), "male", "active");
        Response posrResponse = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794")
                .body(user)
                .when().log().all()
                .post("/public/v2/users");
        Integer userId = posrResponse.jsonPath().get("id");
        System.out.println("UserId: " + userId);
        System.out.println("===================");


//2.PUT -update user details.
        user.setName("Viswa Paridi");
        user.setStatus("inactive");


        given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794")
                .body(user)
                .when().log().all()
                .put("/public/v2/users/" + userId)
                .then().log().all()
                .statusCode(200)
                .and()
                .body("id", equalTo(userId))
                .and()
                .body("name", equalTo(user.getName()))
                .and()
                .body("status", equalTo(user.getStatus()));
    }

}
