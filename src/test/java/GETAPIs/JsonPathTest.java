package GETAPIs;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;

import static io.restassured.RestAssured.given;

import java.util.List;

public class JsonPathTest {

    @Test
    public void getAPIWit_Extractbody_WithData_print() {
        RestAssured.baseURI = "http://ergast.com";
      Response response = given().log().all()
                .when().log().all()
                .get("/api/f1/2017/circuits.json");

      String jsonResponse  = response.asString();
        System.out.println(jsonResponse);
    }
    
    @Test
    public void getProductAPIWithqueryParam() {
        RestAssured.baseURI = "https://fakestoreapi.com";
       Response response = given().log().all()
                .when().log().all()
                .get("/products");

        String jsonResponse = response.asString();
        System.out.println("Resource is:" + jsonResponse);
        List<Float> rateLessthan3 = JsonPath.read(jsonResponse, "$[?(@.rating.rate<3)].rating.rate");
        System.out.println(rateLessthan3.size());
        System.out.println("Rates are: "+rateLessthan3);

    }
}