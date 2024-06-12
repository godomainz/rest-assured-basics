package JIRA;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.*;
import java.io.IOException;
import org.testng.Assert;
import Files.Payload;
import Files.ReusableMethods;

public class JiraTest {

	public static void main(String[] args) throws IOException {
		RestAssured.baseURI = "http://localhost:8080";
		
		SessionFilter session = new SessionFilter();
		
		given().header("Content-Type", "application/json").body(Payload.jiraLogin("rest", "rest@123"))
		.filter(session)
		.when().post("/rest/auth/1/session")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		//Add Comment
		String message = "Hey I have commented from REST API 8";
		String addCommentRes =  given().log().all().pathParam("key", 10005).header("Content-Type", "application/json").body(Payload.jiraAddComment(message))
		.filter(session)
		.when().post("/rest/api/2/issue/{key}/comment")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		 JsonPath addCommentResJS = ReusableMethods.rawToJson(addCommentRes);
		 String commentID = addCommentResJS.getString("id");
		 
		//Add Attachment
		given().pathParam("key", 10005).header("X-Atlassian-Token","no-check").header("Content-Type","multipart/form-data").filter(session).multiPart("file",Payload.getAttachment())
		.when().post("/rest/api/2/issue/{key}/attachments")
		.then().assertThat().statusCode(200).extract().response().asString();

		
		//Get issue
		String issueDetails = given().pathParam("key", 10005).queryParam("fields", "comment").header("Content-Type", "application/json")
		.filter(session)
		.when().get("/rest/api/2/issue/{key}")
		.then().statusCode(200).extract().response().asString();
		System.out.println("*********************************************************");
		JsonPath js = ReusableMethods.rawToJson(issueDetails);
		int commentsCount = js.getInt("fields.comment.comments.size()");
		for(int i=0; i<commentsCount;i++) {
			String curentCommentID = js.get("fields.comment.comments["+i+"].id").toString();
			if(commentID.equals(curentCommentID)) {
				String currentMessage = js.getString("fields.comment.comments["+i+"].body");
				Assert.assertEquals(message, currentMessage);
				System.out.println("Match Found: "+currentMessage);
				break;
			}
		}

	}

}
