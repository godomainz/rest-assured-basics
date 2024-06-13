package OAuth2;

import static io.restassured.RestAssured.*;
import Files.ReusableMethods;
import POJO.GetCourse;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class OAuthTest {

	public static void main(String[] args) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String client_id = "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com";
		String client_secret = "erZOWM9g3UtwNRj340YYaK_W";
		String grant_type = "client_credentials";
		String scope = "trust";
		
		String response = given().log().all().formParams("client_id",client_id).formParams("client_secret", client_secret).formParams("grant_type", grant_type).formParams("scope", scope)
		.when().post("/oauthapi/oauth2/resourceOwner/token").asString();
		
		
		JsonPath js = ReusableMethods.rawToJson(response);
		String access_token = js.getString("access_token");
		
		GetCourse courceDetails = given().queryParams("access_token",access_token)
		.when().get("/oauthapi/getCourseDetails").as(GetCourse.class);
		
		System.out.println(courceDetails.getLinkedIn());

	}

}
