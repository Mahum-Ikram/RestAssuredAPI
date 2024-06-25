import org.testng.annotations.Test;

import file.payload;
import io.restassured.path.json.JsonPath;
import junit.framework.Assert;

public class SumValidation {

	//Verify if Sum of all Course prices matches with Purchase Amount
	
	@Test
	public void sumOfCourses() {
		
		int sum = 0;
		JsonPath js = new JsonPath(payload.CourseData());
		int count = js.getInt("courses.size()");
		
	for (int i=0; i<count; i++)
	{
		int Tprice = js.getInt("courses["+i+"].price");
		int Tcopies = js.getInt("courses["+i+"].copies");
		int amount = Tprice * Tcopies;
		System.out.println(amount);
		sum = sum + amount;
		System.out.println(sum);
	}
		System.out.println(sum);
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(sum, purchaseAmount);

		
		
	}

}
