package XMLAPIs;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class GetCircuitXMLAPITest {

    @Test
    public void xmlTest() {

        RestAssured.baseURI = "http://ergast.com";

        Response response = given()
                .when()
                .get("/api/f1/2017/circuits.xml")
                .then()
                .extract().response();
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);

        //create Object of XML Path

        XmlPath xmlPath = new XmlPath(responseBody);

        //get all circuit names
        List<String> circuitNames = xmlPath.getList("MRData.CircuitTable.Circuit.CircuitName");
        for (String e : circuitNames) {
            System.out.println(e);
        }

        System.out.println("=========END OF CIRCUIT NAMES===========");
        System.out.println();
        //get all circuit Id's
        List<String> circuitIds = xmlPath.getList("MRData.CircuitTable.Circuit.@circuitId");
        for (String e : circuitIds) {
            System.out.println(e);
        }
        System.out.println("=========END OF CIRCUIT ID===========");
        System.out.println();
        //get a location based on query
        String locality = xmlPath.get("**.findAll{it.@circuitId == 'albert_park'}.Location.Locality").toString();
        System.out.println("Circuit city: " +locality);
        System.out.println("=========END OF CIRCUIT Locality===========");
        System.out.println();

        //Get a specific value .to sting not required
        String localitylatValue = xmlPath.get("**.findAll{it.@circuitId == 'albert_park'}Location.@lat");
        String localitylongValue = xmlPath.get("**.findAll{it.@circuitId == 'albert_park'}Location.@long");
        System.out.println("localitylatValue lat : " +localitylatValue + "long" +localitylongValue);
        System.out.println("=========END OF CIRCUIT LocalityValues===========");
        System.out.println();

        //get values using operators
        String localityData = xmlPath.get("**.findAll{it.@circuitId == 'albert_park' || it.@circuitId == 'americas'}.Location.Locality").toString();
        System.out.println("Circuit city: " +localityData);
        System.out.println("=========END OF CIRCUIT localityData===========");
        System.out.println();

        //get circuitname using operators
        String circuitName = xmlPath.get("**.findAll{it.@circuitId == 'albert_park'}.CircuitName");
        System.out.println("Circuit name: " +circuitName);
        System.out.println("=========END OF CIRCUIT circuitname===========");
        System.out.println();

        //get circuiurlusing operators
        String circuitUrl = xmlPath.get("**.findAll{it.@circuitId == 'albert_park'}.@url");
        System.out.println("Circuit url: " +circuitUrl);
        System.out.println("=========END OF CIRCUIT circuitUrl===========");
        System.out.println();

        //get all circuit Url's
        List<String> circuitUrls = xmlPath.getList("MRData.CircuitTable.Circuit.@url");
        for (String e : circuitUrls) {
            System.out.println(e);
        }
        System.out.println("=========END OF CIRCUIT circuitUrls===========");
        System.out.println();
    }
}
