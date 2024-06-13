package OAuth2;

import static io.restassured.RestAssured.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.Assert;
import Files.ReusableMethods;
import POJO.GetCourse;
import POJO.Course.API;
import POJO.Course.WebAutomation;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class OAuthTest {

	public static void main(String[] args) {
		
		String[] expectedCourseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};
		
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
		
		String courseTitle = courceDetails.getCourses().getApi().get(0).getCourseTitle();
		System.out.println(courseTitle);
		
		List<API> apiCourses = courceDetails.getCourses().getApi();
		String expectedCourseTitle = "SoapUI Webservices testing";
		
		for(int i=0; i<apiCourses.size(); i++) {
			courseTitle = apiCourses.get(i).getCourseTitle();
			if(courseTitle.equalsIgnoreCase(expectedCourseTitle)) {
				String price = apiCourses.get(i).getPrice();
				System.out.println(price);
				break;
			}
		}
		
		List<WebAutomation> webAutopationCourses = courceDetails.getCourses().getWebAutomation();
		ArrayList<String> actualTitles = new ArrayList<String>();
		
		for (int i=0; i<webAutopationCourses.size(); i++) {
			WebAutomation course = webAutopationCourses.get(i);
			System.out.println(course.getCourseTitle());
			actualTitles.add(course.getCourseTitle());
		}
		
		List<String> expectedList = Arrays.asList(expectedCourseTitles);
		
		Assert.assertTrue(actualTitles.equals(expectedList));

	}

}
