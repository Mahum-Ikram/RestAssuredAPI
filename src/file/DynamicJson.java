package file;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {

	@Test 
	public void addBook()
	{
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//Add book
		String response = given().header("Content-Type", "application/json")
		.body(payload.addBook("jhsds", "345"))
		.when()
		.post("/Library/Addbook.php")
		.then()
		.assertThat().log().all().statusCode(200).extract().response().asString();
		
		System.out.println("the response is: "+response);
		JsonPath js = new JsonPath(response);
		String id = js.get("ID");
		System.out.println(id);
		
		//Get book
		String getResponse = given()
		.queryParam("ID", id)
		.header("Content-Type", "application/json")
		.when()
		.get("/Library/GetBook.php")
		.then().log().all()
		.assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(getResponse);
		
		
		//Delete book
		String dltResponse = given()
		.header("Content-Type", "application/json")
		.body(payload.dltBook(id))
		.when()
		.delete("/Library/DeleteBook.php")
		.then().log().all()
		.assertThat().statusCode(200).extract().response().asString();
		System.out.println("Deleted Record: "+dltResponse);
	}
	
//	@DataProvider(name="BooksData")
//	
//	public static Object [][] getData() {
//		
//		return new Object [] [] {{"bcss", "2211d2"}, {"bcss", "22112"}, {"bcss", "22112"}};
//		
//	}

}
