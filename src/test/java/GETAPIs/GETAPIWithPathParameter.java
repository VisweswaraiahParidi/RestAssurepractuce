package GETAPIs;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class GETAPIWithPathParameter {

    //qyeryparam vs pathparam
    // query param always in <key, value>
    //path param <anykey, value>

    @Test
    public void getCircuitDataWithPathparam(){
        RestAssured.baseURI = "http://ergast.com";

        given().log().all()
                .pathParams("Year", 2000)
                .when().log().all()
                .get("/api/f1/{Year}/circuits.json")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .body("MRData.CircuitTable.season", equalTo("2000"))
                .and()
                .body("MRData.CircuitTable.Circuits.circuitId", hasSize(17));
    }

    @DataProvider
    public Object[][] getCircuitYearData(){
        return new Object[][]{
                {"2000",17},
                {"2016",21},
                {"2017",20},
                {"2023",22}
        };
    }
    @Test(dataProvider = "getCircuitYearData" )
    public void getCircuitDataWithPathparam_Year_DataProvider(String seasonYear, int totalCircuits){
        RestAssured.baseURI = "http://ergast.com";

        given().log().all()
                .pathParams("year", seasonYear)
                .when().log().all()
                .get("/api/f1/{year}/circuits.json")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .body("MRData.CircuitTable.season", equalTo(seasonYear))
                .and()
                .body("MRData.CircuitTable.Circuits.circuitId", hasSize(totalCircuits));
    }

    @Test(dataProvider = "getCircuitYearData")
    public void getAPIWit_Extractbody_WithData_print(String seasonYear, int totalCircuits) {
        RestAssured.baseURI = "http://ergast.com";
        Response response = given().log().all()
                .pathParams("year", seasonYear)
                .when()
                .get("/api/f1/{year}/circuits.json");
        JsonPath js = response.jsonPath();
        List<String> circuitIds = js.getList("MRData.CircuitTable.Circuits.circuitId", String.class);


        System.out.println("Total number of id's: " + circuitIds.size());
        System.out.println("Circuit Year: "+seasonYear);
        System.out.println("Total Circuits: " +totalCircuits);

        for (int i = 0; i < circuitIds.size(); i++) {
            System.out.println("circuitId: " + circuitIds.get(i));
        }
        System.out.println("================" + "End of: "+seasonYear+"=================");
    }
}
