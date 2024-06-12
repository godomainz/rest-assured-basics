package JIRA;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import static io.restassured.RestAssured.*;
import Files.Payload;
public class JiraTest {

	public static void main(String[] args) {
		RestAssured.baseURI = "http://localhost:8080";
		
		SessionFilter session = new SessionFilter();
		
		given().header("Content-Type", "application/json").body(Payload.jiraLogin("rest", "rest@123"))
		.filter(session)
		.when().post("/rest/auth/1/session")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		String message = "Hey I have commented from REST API";
		given().log().all().pathParam("key", 10005).header("Content-Type", "application/json").body(Payload.jiraAddComment(message))
		.filter(session)
		.when().post("/rest/api/2/issue/{key}/comment")
		.then().log().all().assertThat().statusCode(201);

	}

}
