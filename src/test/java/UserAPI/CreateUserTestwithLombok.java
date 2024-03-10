package UserAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class CreateUserTestwithLombok {

    public static String getRandomEmail() {

        return "Automation" + System.currentTimeMillis() + "@gmail.com";
    }

    @Test
    public void createUserTest() {
        RestAssured.baseURI = "https://gorest.co.in";
        User user = new User("viswa", getRandomEmail(), "male", "active");
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794")
                .body(user)
                .when().log().all()
                .post("/public/v2/users");

        response.prettyPrint();
        Integer userId = response.jsonPath().get("id");
        System.out.println("UserId: " + userId);

        Response getResponse = given().log().all()
                .header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794")
                .when().log().all()
                .get("/public/v2/users/" + userId);

        ObjectMapper mapper = new ObjectMapper();

        try {
            User userResponse = mapper.readValue(getResponse.getBody().asString(), User.class);
            System.out.println("UserID: " + userResponse.getId());
            System.out.println("Username: " + userResponse.getName());
            System.out.println("UserEmail: " + userResponse.getEmail());
            System.out.println("UserGender: " + userResponse.getGender());
            System.out.println("UserStatus: " + userResponse.getStatus());

            Assert.assertEquals(userId, userResponse.getId());
            Assert.assertEquals(user.getName(), userResponse.getName());
            Assert.assertEquals(user.getEmail(), userResponse.getEmail());
            Assert.assertEquals(user.getStatus(), userResponse.getStatus());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void createUserTestWIth_BuilderPattern() {
        RestAssured.baseURI = "https://gorest.co.in";

       User user =  new User.UserBuilder()
                .name("Viswa")
                .email(getRandomEmail())
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

        Response getResponse = given().log().all()
                .header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794")
                .when().log().all()
                .get("/public/v2/users/" + userId);

        ObjectMapper mapper = new ObjectMapper();

        try {
            User userResponse = mapper.readValue(getResponse.getBody().asString(), User.class);
            System.out.println("UserID: " + userResponse.getId());
            System.out.println("Username: " + userResponse.getName());
            System.out.println("UserEmail: " + userResponse.getEmail());
            System.out.println("UserGender: " + userResponse.getGender());
            System.out.println("UserStatus: " + userResponse.getStatus());

            Assert.assertEquals(userId, userResponse.getId());
            Assert.assertEquals(user.getName(), userResponse.getName());
            Assert.assertEquals(user.getEmail(), userResponse.getEmail());
            Assert.assertEquals(user.getStatus(), userResponse.getStatus());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}