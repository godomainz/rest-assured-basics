package BasicClasses;

import Files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		JsonPath js = new JsonPath(Payload.coursePrice());
		
		//Print no of courses returned by API
		int courseCount = js.getInt("courses.size()");
		System.out.println(courseCount);
		
		//Print Purchase Amount
		int purchaseAmount =  js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);
		
		//Print Title of the first course
		String titleFirstCourse = js.get("courses[0].title");
		System.out.println(titleFirstCourse);
		
		//Print All course titles and their respective Prices
		for(int i=0; i<courseCount; i++) {
			String courseTitle = js.get("courses["+i+"].title");
			int coursePrice = js.get("courses["+i+"].price");
			System.out.println(courseTitle+" "+coursePrice);
		}
		
		//Print no of copies sold by RPA Course
		for(int i=0; i<courseCount; i++) {
			String courseTitle = js.get("courses["+i+"].title");
			if(courseTitle.equalsIgnoreCase("RPA")) {
				int copiesCount = js.get("courses["+i+"].copies");
				System.out.println("No of copies sold by RPA Course: "+copiesCount);
				break;
			}
	
		}
	}

}
