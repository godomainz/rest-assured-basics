package BasicClasses;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import Files.Payload;
import Files.ReusableMethods;

public class Basics {
	public static void main(String[] args) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(Payload.addPlace())
		.when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		JsonPath js = ReusableMethods.rawToJson(response);
		String placeId = js.getString("place_id");
		System.out.println(placeId);
		
		// update place
		String newAddress = "70 winter walk, USA";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
		.body(Payload.updatePlace(placeId, newAddress))
		.when().put("maps/api/place/update/json")
		.then().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		// Get place
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath getPlaceJs = ReusableMethods.rawToJson(getPlaceResponse);;
		String actualAddress = getPlaceJs.getString("address");
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, newAddress);
	}


}
