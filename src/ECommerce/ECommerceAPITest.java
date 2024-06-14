package ECommerce;

import static io.restassured.RestAssured.*;

import ECommerce.RequestClasses.User;
import ECommerce.ResponceClasses.LoginResponse;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ECommerceAPITest {

	public static void main(String[] args) {
		
		String baseURI = "https://rahulshettyacademy.com";
		
		User user = new User();
		user.setUserEmail("anshika@gmail.com");
		user.setUserPassword("Iamking@000");
		
		RequestSpecification req = new RequestSpecBuilder().setContentType(ContentType.JSON).setBaseUri(baseURI).build();
		ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		RequestSpecification reqSpec= given().spec(req).body(user);
		LoginResponse loginResponse =  reqSpec.when().post("/api/ecom/auth/login").then().spec(res).extract().response().as(LoginResponse.class);
		
		String token = loginResponse.getToken();
		String userId = loginResponse.getUserId();
		
		System.out.println(token);
		

	}

}
