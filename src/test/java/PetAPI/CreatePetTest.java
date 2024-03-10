package PetAPI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import PetAPI.PetLombok.*;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class CreatePetTest {

    @Test
    public void createPet() {
        RestAssured.baseURI = "https://petstore.swagger.io";

        Category category = new PetLombok.Category(15L, "dog");
        Tag tag = new Tag(12L, "dogson");
        List<Tag> tags = Arrays.asList(tag);
        List<String> photoUrls = Arrays.asList("https://gdog.com", "https://fadd.com");
        PetLombok pet = new PetLombok(200, category, "dog", photoUrls, tags, "available");


        Response response = given()
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                .post("/v2/pet");
        System.out.println(response.statusCode());
        response.prettyPrint();

        //Response
        //de-serilization

        ObjectMapper mapper = new ObjectMapper();

        try {
            PetLombok pl = mapper.readValue(response.getBody().asString(), PetLombok.class);
            System.out.println(pl.getId());
            System.out.println(pl.getName());
            System.out.println(pl.getStatus());

            System.out.println(pl.getCategory().getId());
            System.out.println(pl.getCategory().getName());

            System.out.println(pl.getPhotoUrls());

            System.out.println(pl.getTags().get(0).getId());
            System.out.println(pl.getTags().get(0).getName());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void createPet_with_BuilderPattern() {
        RestAssured.baseURI = "https://petstore.swagger.io";

        Category category = new Category.CategoryBuilder()
                .id(1L)
                .name("Animal")
                .build();

        Tag tag = new Tag.TagBuilder()
                .id(2L)
                .name("Donkey")
                .build();
        Tag tag1 = new Tag.TagBuilder()
                .id(3L)
                .name("Donkeyson")
                .build();

        PetLombok plom = new PetLombok.PetLombokBuilder()
                .id(15)
                .category(category)
                .name("Donkey Fammily")
                .photoUrls(Arrays.asList("httsps://donkey.com", "https://donkeyson.com"))
                .tags(Arrays.asList(tag, tag1))
                .status("available")
                .build();

        Response response = given()
                .contentType(ContentType.JSON)
                .body(plom)
                .when()
                .post("/v2/pet");
        System.out.println(response.statusCode());
        response.prettyPrint();

        //Response
        //de-serilization

        ObjectMapper mapper = new ObjectMapper();

        try {
            PetLombok pl = mapper.readValue(response.getBody().asString(), PetLombok.class);
            System.out.println(pl.getId());
            System.out.println(pl.getName());
            System.out.println(pl.getStatus());

            System.out.println(pl.getCategory().getId());
            System.out.println(pl.getCategory().getName());

            System.out.println(pl.getPhotoUrls());

            System.out.println(pl.getTags().get(0).getId());
            System.out.println(pl.getTags().get(0).getName());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }
}
