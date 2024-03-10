package Specificationconcept;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class RequestSpecBuilderTest {

    public static RequestSpecification user_req_spec() {

        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://gorest.co.in")
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794")
                .build();
        return requestSpec;
    }

    @Test
    public void getUser_with_Request_spec() {

        RestAssured.given().log().all()
                .spec(user_req_spec())
                .get("public/v2/users/")
                .then().log().all()
                .assertThat()
                .statusCode(200);

    }

    @Test
    public void getUser_with_queryParam_Request_spec() {

        RestAssured.given().log().all()
                .queryParam("name","naveen")
                .queryParam("status","active")
                .spec(user_req_spec())
                .get("public/v2/users/")
                .then()
                .assertThat()
                .statusCode(200);

    }
}
