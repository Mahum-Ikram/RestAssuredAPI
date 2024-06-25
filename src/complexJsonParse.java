import file.payload;
import io.restassured.path.json.JsonPath;

public class complexJsonParse {

	
	public static void main(String[] args) {
		
	JsonPath js = new JsonPath(payload.CourseData());
	
	//Print No of courses returned by API
	
	int count = js.getInt("courses.size()");
	System.out.println(count);
	
	//Print Purchase Amount
	
	int totalAmount = js.getInt("dashboard.purchaseAmount");
	System.out.println(totalAmount);
	
	//Print Title of the first course
	
	String title = js.get("courses[0].title");
	System.out.println(title);
	
	 //Print All course titles and their respective Prices
	
		for (int i = 0; i < count; i++) {
			String coursesName = js.get("courses[" + i + "].title");

			System.out.println("Course Name: " + coursesName);
			System.out.println(js.get("courses[" + i + "].price").toString());

		}
	
		// Print no of copies sold by RPA Course

		System.out.println("RPA");
		
		for (int i= 0;i<count;i++) 
		{
			String CourseName = js.get("courses["+i+"].title");
			if (CourseName.equalsIgnoreCase("RPA")) 
			{
				int copies = (js.get("courses["+i+"].copies"));
				System.out.println(copies);
				break;
			}

		}
		
//	//Verify if Sum of all Course prices matches with Purchase Amount
//		
//		for (int i = 0; i < count; i++) {
//
		
//		}
	
	
	}}
