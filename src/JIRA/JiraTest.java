package JIRA;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import static io.restassured.RestAssured.*;
import java.io.File;
import java.io.IOException;

import Files.Payload;
public class JiraTest {

	public static void main(String[] args) throws IOException {
		RestAssured.baseURI = "http://localhost:8080";
		
		SessionFilter session = new SessionFilter();
		
		given().header("Content-Type", "application/json").body(Payload.jiraLogin("rest", "rest@123"))
		.filter(session)
		.when().post("/rest/auth/1/session")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		//Add Comment
		String message = "Hey I have commented from REST API 3";
		given().log().all().pathParam("key", 10005).header("Content-Type", "application/json").body(Payload.jiraAddComment(message))
		.filter(session)
		.when().post("/rest/api/2/issue/{key}/comment")
		.then().log().all().assertThat().statusCode(201);
		
		//Add Attachment
		given().pathParam("key", 10005).header("X-Atlassian-Token","no-check").header("Content-Type","multipart/form-data").filter(session).multiPart("file",Payload.getAttachment())
		.when().post("/rest/api/2/issue/{key}/attachments")
		.then().assertThat().statusCode(200);

	}

}
