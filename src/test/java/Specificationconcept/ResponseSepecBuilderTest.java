package Specificationconcept;

import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class ResponseSepecBuilderTest {

    public static ResponseSpecification user_req_spec_200_OK() {
        ResponseSpecification user_req_spec_200_OK = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectHeader("Server", "cloudflare")
                .build();
        return user_req_spec_200_OK;
    }

    public static ResponseSpecification user_req_spec_401_AUTH_FAIL() {
        ResponseSpecification res_spec_401_AUTH_FAIL = new ResponseSpecBuilder()
                .expectStatusCode(401)
                .expectHeader("Server", "cloudflare")
                .build();
        return res_spec_401_AUTH_FAIL;
    }

    public static ResponseSpecification user_req_spec_200_OK_WIth_Body() {
        ResponseSpecification user_req_spec_200_OK_WIth_Body = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectBody("$.size()", equalTo(10))
                .expectBody("id",hasSize(10))
                .expectHeader("Server", "cloudflare")
                .build();
        return user_req_spec_200_OK_WIth_Body;
    }

    @Test
    public void getUser_with_Request_spec_200_OK() {

        RestAssured.baseURI = "https://gorest.co.in";
        given()
                .header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794")
                .when()
                .get("/public/v2/users")
                .then().log().all()
                .assertThat()
                .spec(user_req_spec_200_OK_WIth_Body());

    }

    @Test
    public void getUser_with_Request_spec_401_AUTH_FAIL() {

        RestAssured.baseURI = "https://gorest.co.in";
        given()
                .header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a479499")
                .when()
                .get("/public/v2/users")
                .then().log().all()
                .assertThat()
                .spec(user_req_spec_401_AUTH_FAIL());

    }

}
