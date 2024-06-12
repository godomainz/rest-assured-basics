package BasicClasses;

import org.testng.annotations.Test;

import Files.Payload;
import Files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {
	
	@Test
	private void addBook() {
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all().header("Content-Type","application/json").body(Payload.addBook("bcd1","2271"))
		.when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ReusableMethods.rawToJson(response);
		String id = js.get("ID");
		

	}

}
