package MultiBody;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class BodyAPITest {

    @Test
    public void bodyWithTextTest() {
        RestAssured.baseURI = "https://httpbin.org/";
        String payLoad = "Hi This is Viswa";
        Response response = given()
                .contentType(ContentType.TEXT)
                .body(payLoad)
                .when()
                .post("/post");
        response.prettyPrint();

        String resp = response.jsonPath().get("data");
        System.out.println(response.statusCode());
        System.out.println(resp);

    }

    @Test
    public void bodyWithJavaSCryptTest() {
        RestAssured.baseURI = "https://httpbin.org/";
        String payLoad = "function login(){\n" +
                "let x =10;\n" +
                "let y=20;\n" +
                "console.log(x+y);\n" +
                "}";
        Response response = given()
                .header("Content-Type", "application/javascript")
                .body(payLoad)
                .when()
                .post("/post");

        response.prettyPrint();
        String data = response.jsonPath().get("data");
        System.out.println(response.statusCode());
        System.out.println(data);
    }

    @Test
    public void bodyWithHTMLTest() {
        RestAssured.baseURI = "https://httpbin.org/";
        String payLoad = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\t<meta charset=\"utf-8\">\n" +
                "\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "\t<title></title>\n" +
                "</head>\n" +
                "<body>\n" +
                "</body>\n" +
                "</html>";
        Response response = given()
                .contentType(ContentType.HTML)
                .body(payLoad)
                .when()
                .post("/post");

        response.prettyPrint();
        String data = response.jsonPath().get("data");
        System.out.println(response.statusCode());
        System.out.println(data);
    }

    @Test
    public void bodyWithXMLTest() {
        RestAssured.baseURI = "https://httpbin.org/";
        String payLoad = "<note>\n" +
                "<to>Tove</to>\n" +
                "<from>Jani</from>\n" +
                "<heading>Reminder</heading>\n" +
                "<body>Don't forget me this weekend!</body>\n" +
                "</note>";
        Response response = given()
                //  .header("Content-Type", "application/xml")
                .contentType(ContentType.XML)
                .body(payLoad)
                .when()
                .post("/post");

        response.prettyPrint();
        String data = response.jsonPath().get("data");
        System.out.println(response.statusCode());
        System.out.println(data);
    }

    @Test
    public void bodyWithFormDataMultiPartTest() {
        RestAssured.baseURI = "https://httpbin.org/";
        Response response = given()
                .contentType(ContentType.MULTIPART)
                .multiPart("name", "testing")
                .multiPart("filename", new File("C:\\Users\\viswe\\OneDrive\\Desktop\\LICRenewalpayment(1).pdf"))
                //  .header("Content-Type", "application/xml")
                .when()
                .post("/post");

        response.prettyPrint();
        System.out.println(response.statusCode());
    }

    @Test
    public void bodyWithBinaryTest() {
        RestAssured.baseURI = "https://httpbin.org/";
        Response response = given()
                .header("Content-Type", "application/pdf")
                .body(new File("C:\\Users\\viswe\\OneDrive\\Desktop\\LICRenewalpayment(1).pdf"))
                //  .header("Content-Type", "application/xml")
                .when()
                .post("/post");

        response.prettyPrint();
        System.out.println(response.statusCode());
    }
}
