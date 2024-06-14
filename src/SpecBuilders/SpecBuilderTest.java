package SpecBuilders;
import static io.restassured.RestAssured.*;
import java.util.ArrayList;
import java.util.List;

import POJOSerialize.Location;
import POJOSerialize.Place;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilderTest {


	public static void main(String[] args) {
		String baseURI = "https://rahulshettyacademy.com";
		
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
		
		RequestSpecification req = new RequestSpecBuilder().setContentType(ContentType.JSON).setBaseUri(baseURI).addQueryParam("key", "qaclick123").build();
		ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
//		String response = given().queryParam("key", "qaclick123").body(place)
//		.when().post("/maps/api/place/add/json")
//		.then().assertThat().statusCode(200).extract().response().asString();
		
		String response = given().spec(req).body(place).post("/maps/api/place/add/json")
		.then().spec(res).extract().asString();
		

		System.out.println(response);

	}

}
