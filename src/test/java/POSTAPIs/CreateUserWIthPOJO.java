package POSTAPIs;

import POJO.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserWIthPOJO {

public String getRandomEmailid(){

    //1. Direct supply the Json String
    //2. Pass the Json File
    //3. POJO- to Json Objects - with the help of Jacson/rest assured dependency
    return "Automation"+System.currentTimeMillis()+"@mail.com";
   // return "APIAutomation"+ UUID.randomUUID()+"@Gmail.com";
}
    @Test
    public void addUserTest() {

        User user = new User("Viswa", getRandomEmailid(), "male", "active");
        RestAssured.baseURI = "https://gorest.co.in";
        int userId = given().log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794")
                .when()
                .post("/public/v2/users/")
                .then()
                .assertThat()
                .statusCode(201)
                .body("name", equalTo(user.getName()))
                .extract()
                .path("id");


        System.out.println("user id :" + userId);
        given().log().all()
                .header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794")
                .when()
                .get("/public/v2/users/" + userId)
                .then()
                .assertThat()
                .statusCode(200)
                .body("id", equalTo(userId))
                .and()
                .body("name", equalTo(user.getName()))
                .and()
                .body("status", equalTo(user.getStatus()));


        System.out.println("Tokenuid is: " + userId);

    }
}
