package POJOSerialize;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;
import io.restassured.RestAssured;

public class SerializeTest {
	
	public static void main(String[] args){
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		Location loc = new Location();
		loc.setLat(-38.383494);
		loc.setLng(33.427362);
		
		Place place = new Place();
		place.setLocation(loc);
		place.setAccuracy(50);
		place.setName("Frontline house");
		place.setPhone_number("(+91) 983 893 3937");
		place.setAddress("29, side layout, cohen 09");
		List<String> types = new ArrayList<String>();
		types.add("shoe park");
		types.add("shop");
		place.setTypes(types);
		place.setWebsite("http://google.com");
		place.setLanguage("French-IN");
		
		
		String responce = given().queryParam("key", "qaclick123").body(place)
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(responce);
	}

}
