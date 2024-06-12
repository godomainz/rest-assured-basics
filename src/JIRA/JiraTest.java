package JIRA;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import Files.Payload;
public class JiraTest {

	public static void main(String[] args) {
		RestAssured.baseURI = "http://localhost:8080";
		
		
		given().log().all().pathParam("key", 10005).header("Content-Type", "application/json").body(Payload.addComment())
		.when().post("/rest/api/2/issue/{key}/comment")

	}

}
