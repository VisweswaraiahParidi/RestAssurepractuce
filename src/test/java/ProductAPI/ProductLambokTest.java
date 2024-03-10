package ProductAPI;

import FakeAPI.ProductLombok;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ProductLambokTest {

    @Test
    public void getProductsTestWithPOJO_LOMBOK() {
        RestAssured.baseURI = "https://fakestoreapi.com";

        Response response = given()
                .when()
                .get("/products");

        //json to pojo mapping:de-serialization

        ObjectMapper mapper = new ObjectMapper();
        try {
            ProductLombok[] products = mapper.readValue(response.getBody().asString(), ProductLombok[].class);

            for(ProductLombok p : products) {
                System.out.println("ID: " + p.getId());
                System.out.println("Title " + p.getTitle());
                System.out.println("Price :" + p.getPrice());
                System.out.println("Description: " + p.getDescription());
                System.out.println("Category: " + p.getCategory());
                System.out.println("Image: " + p.getImage());
                System.out.println("Rate: " + p.getRating().getRate());
                System.out.println("Count: " + p.getRating().getCount());

                System.out.println("--------------");
                System.out.println("END");
            }


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
