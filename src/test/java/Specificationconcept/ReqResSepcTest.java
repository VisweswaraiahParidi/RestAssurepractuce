package Specificationconcept;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class ReqResSepcTest {

    public static RequestSpecification user_req_spec() {

        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://gorest.co.in")
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794")
                .build();
        return requestSpec;
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
    public void getUser_Req_Res_Spec_Test(){

        given()
                .spec(user_req_spec())
                .get("/public/v2/users")
                .then()
                .assertThat()
                .spec(user_req_spec_200_OK_WIth_Body());
    }

}
