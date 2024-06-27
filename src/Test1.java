import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static org.hamcrest.Matchers.*;

import file.payload;

import static io.restassured.RestAssured.*;

public class Test1 {

	
	public static void main(String[] args) {
    //TODO Auto-generated method stub
    //given - all input details
    //when - run the API
    //then - validate the response
		
		
		//Post add place
		baseURI = "https://rahulshettyacademy.com";
		
    	String response =  given()
    	.queryParam("key", "qaclick123").header("Content-Type", "application/json")
    	.body(payload.AddPlace())
    	.when()
    	.post("maps/api/place/add/json")
    	.then()
    	.assertThat().log().all()
    	.statusCode(200)
    	.body("scope", equalTo("APP"))
    	.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
    	
    	System.out.println(response);
    	
    	JsonPath js = new JsonPath(response); //for parsing Json
    	
    	String placeID = js.getString("place_id");
    	System.out.println(placeID);
    	
    	//Add place -> Update place with new address -> Get place to validate if new address is present or not
    	
    	//update place
    	
    	String newAddress = "70 winter walk, USA";
    	
    	given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
    	.body("{\r\n"
    			+ "\"place_id\":\""+placeID+"\",\r\n"
    			+ "\"address\":\""+newAddress+"\",\r\n"
    			+ "\"key\":\"qaclick123\"\r\n"
    			+ "}")
    	.when()
    	.put("maps/api/place/update/json")
    	.then()
    	.log().all()
    	.assertThat()
    	.statusCode(200)
    	.body("msg", equalTo("Address successfully updated"));
    	
    	//get place
    	//http://rahulshettyacademy.com/maps/api/place/get/json?place_id=707e965cb5fe5b3a43de0d5e76b70f16&key=qaclick123
    	 String getPlaceResponse = given().log().all()
    	    	.queryParam("key", "qaclick123")
    	    	.queryParam("place_id", placeID)
    	    	.header("Content-Type", "application/json")
    	   	    .when().get("maps/api/place/get/json")
    	    	.then()
    	    	.log().all()
    	    	.assertThat().statusCode(200).extract().response().asString();
    	
    	JsonPath js1 = new JsonPath(getPlaceResponse);
    	String actualAddress = js1.getString("address");
    	 System.out.println(actualAddress);
    	
	}

}
