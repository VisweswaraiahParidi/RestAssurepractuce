package UserAPI;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateandUpdateUserWithLombokBulderPatteren {

    public static String getRandonEmail() {
        return "Automation" + System.currentTimeMillis() + "@gmail.com";
    }

    //POST Create user with Lombok Builder Pattern
    @Test
    public void createUserwithBulderPattern() {

        RestAssured.baseURI = "https://gorest.co.in";

        User user = new User.UserBuilder()
                .name("Viswa")
                .email(getRandonEmail())
                .gender("male")
                .status("active")
                .build();

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794")
                .body(user)
                .when().log().all()
                .post("/public/v2/users");
        Integer userId = response.jsonPath().get("id");
        System.out.println("UserId: " + userId);

//PUT - update User

        user.setName("Viswa Narayana");
        user.setStatus("active");
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
