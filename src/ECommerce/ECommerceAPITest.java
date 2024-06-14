package ECommerce;

import static io.restassured.RestAssured.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.testng.Assert;
import ECommerce.RequestClasses.Order;
import ECommerce.RequestClasses.Orders;
import ECommerce.RequestClasses.User;
import ECommerce.ResponceClasses.AddProductResponse;
import ECommerce.ResponceClasses.CreateOrderResponse;
import ECommerce.ResponceClasses.DeleteProductResponse;
import ECommerce.ResponceClasses.LoginResponse;
import Files.Payload;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
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
		
		RequestSpecification reqSpec= given().relaxedHTTPSValidation().spec(req).body(user);
		LoginResponse loginResponse =  reqSpec.when().post("/api/ecom/auth/login").then().spec(res).extract().response().as(LoginResponse.class);
		
		String token = loginResponse.getToken();
		String userId = loginResponse.getUserId();
		
		System.out.println(token);
		
		RequestSpecification addProductBaseReqSpec = new RequestSpecBuilder().addHeader("Authorization", token).setBaseUri(baseURI).build();
		RequestSpecification addProductReqSpec =given().log().all().spec(addProductBaseReqSpec)
		.param("productName", "Addidas Shirt")
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
		
		Order order = new Order();
		order.setCountry("Sri Lanka");
		order.setProductOrderedId(productId);
		
		List<Order> ordersList = new ArrayList<Order>();
		ordersList.add(order);
		
		Orders orders = new Orders();
		orders.setOrders(ordersList);
		
		RequestSpecification createOrderBaseReqSpec = new RequestSpecBuilder().addHeader("Authorization", token).setBaseUri(baseURI).setContentType(ContentType.JSON).build();
		RequestSpecification createOrderReqSpec= given().log().all().spec(createOrderBaseReqSpec).body(orders);
		CreateOrderResponse createOrderResponse = createOrderReqSpec.when().post("/api/ecom/order/create-order").then().extract().as(CreateOrderResponse.class);
		
		System.out.println(createOrderResponse.getOrders());
		
		RequestSpecification deleteProductBaseReqSpec = new RequestSpecBuilder().addHeader("Authorization", token).setBaseUri(baseURI).setContentType(ContentType.JSON).addPathParam("productId", productId).build();
		DeleteProductResponse deleteProductResponse= given().spec(deleteProductBaseReqSpec).delete("/api/ecom/product/delete-product/{productId}").then().extract().as(DeleteProductResponse.class);

		System.out.println(deleteProductResponse.getMessage());
		Assert.assertEquals("Product Deleted Successfully", deleteProductResponse.getMessage());
	}

}
