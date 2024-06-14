package ECommerce;

import static io.restassured.RestAssured.*;

import java.io.IOException;

import ECommerce.RequestClasses.User;
import ECommerce.ResponceClasses.AddProductResponse;
import ECommerce.ResponceClasses.LoginResponse;
import Files.Payload;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ECommerceAPITest {

	public static void main(String[] args) throws IOException {
		
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
		
		RequestSpecification addProductBaseReqSpec = new RequestSpecBuilder().addHeader("Authorization", token).setBaseUri(baseURI).build();
		RequestSpecification addProductReqSpec =given().log().all().spec(addProductBaseReqSpec)
		.param("productName", "Addidas Shirt1")
		.param("productAddedBy", userId)
		.param("productCategory", "fashion")
		.param("productSubCategory", "shirts")
		.param("productPrice", "11500")
		.param("productDescription", "Addias Originals")
		.param("productFor", "women")
		.multiPart("productImage",Payload.getProductImage());
		
		AddProductResponse addProductRes = addProductReqSpec.when().post("/api/ecom/product/add-product")
				.then().extract().response().as(AddProductResponse.class);
		
		String productId = addProductRes.getProductId();
		
		System.out.println(productId);

	}

}
