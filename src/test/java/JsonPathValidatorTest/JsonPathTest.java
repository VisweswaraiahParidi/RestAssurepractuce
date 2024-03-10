package JsonPathValidatorTest;

import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class JsonPathTest {

    @Test
    public void getAPIWit_Extractbody_WithData_print() {
        RestAssured.baseURI = "http://ergast.com";
        Response response = given().log().all()
                .when().log().all()
                .get("/api/f1/2017/circuits.json");

        String jsonResponse = response.asString();
        System.out.println("Resource is:" + jsonResponse);

        List<String> countryList = JsonPath.read(jsonResponse, "$..Circuits..country");
        System.out.println(countryList.size());
        System.out.println(countryList);
    }

    @Test
    public void getProductinfowithratingLess3() {
        RestAssured.baseURI = "https://fakestoreapi.com";
        Response response = given().log().all()
                .when().log().all()
                .get("/products");

        String jsonResponse = response.asString();
        System.out.println("Resource is:" + jsonResponse);
        List<Float> rateLessthan3 = JsonPath.read(jsonResponse, "$[?(@.rating.rate<3)]..rate");
        System.out.println(rateLessthan3.size());
        System.out.println("Rates are: " + rateLessthan3);

        // with two attributes
        List<Map<String, Object>> jewelList = JsonPath.read(jsonResponse, "$[?(@.category=='jewelery')].[\"title\",\"price\"]");
        System.out.println(jewelList.size());
        System.out.println("Rates are: " + jewelList);

        for (Map<String, Object> product : jewelList) {
            String title = (String) product.get("title");
            Object price = product.get("price");
            System.out.println("Tile is:" + title);
            System.out.println("Price is: " + price);
            System.out.println("------------------");
        }
        System.out.println("----END--------------");
        // with Three attributes
        List<Map<String, Object>> attributes3 = JsonPath.read(jsonResponse, "$[?(@.category == 'jewelery')].[\"title\",\"price\",\"category\"]");
        List<Map<String, Object>> attributes4 = JsonPath.read(jsonResponse, "$[?(@.category == 'jewelery')].[\"rate\",\"count\"]");

        System.out.println(attributes3.size());


        for (Map<String, Object> listAttributes : attributes3) {
            for (Map<String, Object> listAttributes2 : attributes4) {
                Integer count = (Integer) listAttributes2.get("count");
                Float rate = (Float) listAttributes2.get("rate");
                String titl = (String) listAttributes.get("title");
                Object pric = listAttributes.get("price");
                String category = (String) listAttributes.get("category");


                System.out.println("Tile is:" + titl);
                System.out.println("category is:" + category);
                System.out.println("Price is: " + pric);
                System.out.println("Count is:" + count);
                System.out.println("rate is: " + rate);
                System.out.println("-------------------------");
            }
            System.out.println("----END--------------");
        }
    }
}