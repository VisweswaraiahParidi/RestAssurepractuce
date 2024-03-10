package GETAPIs;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GETAPIrequestTestWithBDD {
    RequestSpecification request;

    @Test
    public void getProductsTest() {
        RestAssured.baseURI = "https://fakestoreapi.com";
        given().log().all()
                .when().log().all()
                .get("/products")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .header("Connection", "keep-alive")
                .and()
                .body("$.size()", equalTo(20))
                .body("id", is(notNullValue()))
                .and()
                .body("title", hasItem("Mens Cotton Jacket"));

        String connection = "keep-alive";
        Assert.assertEquals(connection, "keep-alive");
    }

    @Test
    public void getUserAPITest() {

        RestAssured.baseURI = "https://gorest.co.in";

        given().log().all()
                .header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794")
                .when().log().all()
                .get("/public/v2/users/")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .body("$.size()", equalTo(10))
                .and()
                .body("id", is(notNullValue()))
                .and()
                .contentType(ContentType.JSON);
    }

    @Test
    public void getProductAPI() {
        RestAssured.baseURI = "https://fakestoreapi.com";
        given().log().all()
                .queryParam("limit", 5)
                .when().log().all()
                .get("/products")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .body("category", hasItem("men's clothing"));

    }

    @Test
    public void getProductAPIWithqueryParam() {
        RestAssured.baseURI = "https://fakestoreapi.com";
        given().log().all()
                .queryParam("limit", 5)
                .when().log().all()
                .get("/products")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .body("category", hasItem("men's clothing"));

    }

    @Test
    public void getProductAPIWit_Extractbody_WithArray() {
        RestAssured.baseURI = "https://fakestoreapi.com";
        Response response = given().log().all()
                .queryParam("limit", 5)
                .when().log().all()
                .get("/products");

        JsonPath js = response.jsonPath();

        int firstProduId = js.getInt("[0].id");
        String productTitle = js.getString("[0].title");
        float productPrice = js.getFloat("[0].price");
        int count = js.getInt("[0].rating.count");
        Assert.assertEquals(count, 120);
        System.out.println("First Prodcut Id is: " + firstProduId + " Title is: " + productTitle + "  Product price : " + productPrice);
    }

    @Test
    public void getProductAPIWit_Extractbody() {
        RestAssured.baseURI = "https://fakestoreapi.com";
        Response response = given().log().all()
                .queryParam("limit", 10)
                .when().log().all()
                .get("/products");

        JsonPath js = response.jsonPath();
        List<Integer> idList = js.getList("id");
        List<String> titleList = js.getList("title");
        //    List<Float> rateList = js.getList("rating.rate");
        List<Float> rateList = js.getList("rating.rate", Float.class);
        List<Integer> countList = js.getList("rating.count");

        System.out.println("Total number of id's: " + idList.size());
        System.out.println(idList);
        System.out.println(titleList);

        for (int i = 0; i < idList.size(); i++) {
            int id = idList.get(i);
            String title = titleList.get(i);
            Float rate = rateList.get(i);
            int count = countList.get(i);

            System.out.println("Id: " + id);
            System.out.println("Title:: " + title);
            System.out.println("Rate:  " + rate);
            System.out.println("Count: " + count);
            System.out.println("================");
        }
    }

    @Test
    public void getuserAPIWit_Extractbody_withJson() {
        RestAssured.baseURI = "https://gorest.co.in";

        Response response = given().log().all()
                .header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794")
                .when().log().all()
                .get("/public/v2/users/6260128");
        JsonPath js = response.jsonPath();
        int id = js.getInt("id");
        Assert.assertEquals(id, 6260128);
        System.out.println("Id is: " + js.getInt("id"));
    }

    @Test
    public void getuserAPIWit_Extractbody_withJson_Extract() {
        RestAssured.baseURI = "https://gorest.co.in";

//       int id = given().log().all()
//                .header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794")
//                .when().log().all()
//                .get("/public/v2/users/6260128")
//                .then()
//                .extract().path("id");
//        System.out.println("Id is: "+id);

        Response response = given().log().all()
                .header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794")
                .when().log().all()
                .get("/public/v2/users/6260128")
                .then()
                .extract()
                .response();

        int resId = response.path("id");
        String resEmail = response.path("email");
        System.out.println("Id is: " + resId + "  email: " + resEmail);

    }
}
