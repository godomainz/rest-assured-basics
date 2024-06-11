package BasicClasses;

import org.testng.Assert;
import org.testng.annotations.Test;

import Files.Payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {

	@Test
	public void sumOfCourses() {
		JsonPath js = new JsonPath(Payload.coursePrice());
		int purchaseAmount =  js.getInt("dashboard.purchaseAmount");
		int courseCount = js.getInt("courses.size()");
		
		//Verify if Sum of all Course prices matches with Purchase Amount
		int total = 0;
		for(int i=0; i<courseCount; i++) {
			int coursePrice = js.get("courses["+i+"].price");
			int copiesCount = js.get("courses["+i+"].copies");
			int courseAmount = coursePrice * copiesCount;
			total = total + courseAmount;
		}
		
		Assert.assertEquals(total, purchaseAmount);
	}
}
