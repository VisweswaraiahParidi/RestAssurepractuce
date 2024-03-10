package GETAPIs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GETAllUsersAPIrestTest {

	@Test
	public void getUserAPITest() {
		// Request
		RestAssured.baseURI = "https://gorest.co.in";
		RequestSpecification request = RestAssured.given();
		request.header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794");
		Response response = request.get("public/v2/users/");

		// Status code
		int statusCode = response.statusCode();
		System.out.println("Statuis code is:  " + statusCode);

		// Verification Point
		Assert.assertEquals(statusCode, 200);

		// Status Message
		String statusMsg = response.statusLine();
		System.out.println("Status Message is: " + statusMsg);

		// fecth the body from response
		response.prettyPrint();

		// fetch Headers
		String contentType = response.header("Content-Type");
		System.out.println("Heade is" + contentType);
		System.out.println("------------------------------------------");

		// fetch all headers
		List<Header> headersList = response.headers().asList();
		System.out.println("No of Headers are availble are :" + headersList.size());

		// Print fetched values
		for (Header h : headersList) {
			System.out.println(h.getName() + ":" + h.getValue());
		}

	}

	@Test
	public void getUserWithqueryparameterAPITest() {
		// Request
		RestAssured.baseURI = "https://gorest.co.in";
		RequestSpecification request = RestAssured.given();
		
		request.param("name", "naveen");
		request.param("status", "active");
		request.header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794");
	//	Response response = request.get("public/v2/users/?name=naveen"); //But this is not a good practice 
		Response response = request.get("public/v2/users/"); //But this is not a good practice 

		// Status code
		int statusCode = response.statusCode();
		System.out.println("Statuis code is:  " + statusCode);

		// Verification Point
		Assert.assertEquals(statusCode, 200);

		// Status Message
		String statusMsg = response.statusLine();
		System.out.println("Status Message is: " + statusMsg);

		// fecth the body from response
		response.prettyPrint();

	}
	
	@Test
	public void getUserWithqueryparameter_WithhashMap_APITest() {
		// Request
		RestAssured.baseURI = "https://gorest.co.in";
		RequestSpecification request = RestAssured.given();
		
		Map<String, String> queryParamMap = new HashMap<String,String>();
		queryParamMap.put("name", "naveen");
		queryParamMap.put("status", "active");
		request.queryParams(queryParamMap);

		request.header("Authorization", "Bearer 84401bc3bc5e2259f0ac5820f121ecab97ab9be7c1337c03936a89cbe51a4794");
		Response response = request.get("public/v2/users/"); //But this is not a good practice 

		// Status code
		int statusCode = response.statusCode();
		System.out.println("Statuis code is:  " + statusCode);

		// Verification Point
		Assert.assertEquals(statusCode, 200);

		// Status Message
		String statusMsg = response.statusLine();
		System.out.println("Status Message is: " + statusMsg);

		// fecth the body from response
		response.prettyPrint();

	}
}

