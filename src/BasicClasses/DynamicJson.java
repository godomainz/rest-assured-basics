package BasicClasses;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import Files.Payload;
import Files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;

public class DynamicJson {
	final String dataProviderName = "BooksData";
	
	@Test(dataProvider = dataProviderName)
	private void addBook(String isbn, String aisle) {
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all().header("Content-Type","application/json").body(Payload.addBook(isbn,aisle))
		.when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ReusableMethods.rawToJson(response);
		String id = js.get("ID");
	}
	
	@DataProvider(name=dataProviderName)
	private Object[][] getData() {
		return new Object[][] {{"wqe","123"}, {"asds","432"}, {"zxxz","6575"}};

	}
}
