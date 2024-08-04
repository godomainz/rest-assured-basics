package Categories;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class Categories {
	public static void main(String[] args) {
	
		String name = "test";
		String imagePath = "test.jpg";
		
		RestAssured.baseURI = "http://127.0.0.1:8000/";
//		String jsonPayload = "{ \"name\": \"Desserts\", \"imagePath\": \"path/to/image.jpg\" }";
		// Path to the JSON file
        String jsonFilePath = "src/Categories/categories.json";
        
        // Read and parse the JSON file
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode categoriesNode;
		
//		String response = given().log().all().auth()
//                .preemptive()
//                .basic("akila", "123")
//                .header("Content-Type", "application/json").body(jsonPayload)
//				.when().post("/recipes/categories/").then()
//                .statusCode(201) // Check if the status code is 201 Created
//                .extract()
//                .response().asString();
//		System.out.println(response);
        try {
            categoriesNode = objectMapper.readTree(new File(jsonFilePath)).get("categories");

            // Convert JsonNode to an array of JsonNode
            JsonNode[] categoriesArray = objectMapper.convertValue(categoriesNode, JsonNode[].class);

            for (JsonNode category : categoriesArray) {
                String jsonPayload = category.toString();

                // Sending POST request with basic authentication
                Response response = RestAssured.given()
                    .auth()
                    .preemptive()
                    .basic("akila", "123")
                    .header("Content-Type", "application/json")
                    .body(jsonPayload)
                    .when()
                    .post("/recipes/categories/")
                    .then()
                    .statusCode(201) // Check if the status code is 201 Created
                    .extract()
                    .response();

                // Print the response
                System.out.println("Response for category " + category.get("name").asText() + ": " + response.asString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
