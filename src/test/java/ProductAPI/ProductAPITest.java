package ProductAPI;

import FakeAPI.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class ProductAPITest {
    @Test
    public void getProductsTestWithPOJO() {
        RestAssured.baseURI = "https://fakestoreapi.com";

        Response response = given()
                .when()
                .get("/products");

        //json to pojo mapping:de-serialization

        ObjectMapper mapper = new ObjectMapper();
        try {
            Product product[] = mapper.readValue(response.getBody().asString(), Product[].class);

            for(Product p : product) {
                System.out.println("ID: " + p.getId());
                System.out.println("Title " + p.getTitle());
                System.out.println("Price :" + p.getPrice());
                System.out.println("Description: " + p.getDescription());
                System.out.println("Category: " + p.getCategory());
                System.out.println("Image: " + p.getImage());
                System.out.println("Rate: " + p.getRating().getRate());
                System.out.println("Count: " + p.getRating().getCount());

                System.out.println("--------------");
            }


        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


}
