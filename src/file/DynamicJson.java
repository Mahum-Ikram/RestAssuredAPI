package file;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {

	@Test (dataProvider = "BooksData")
	public void addBook(String isbn, String aisle)
	{
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//Add book
		String response = given().header("Content-Type", "application/json")
		.body(payload.addBook(isbn, aisle))
		.when()
		.post("/Library/Addbook.php")
		.then()
		.assertThat().log().all().statusCode(200).extract().response().asString();
		
		System.out.println("the response is: "+response);
		JsonPath js = new JsonPath(response);
		String id = js.get("ID");
		System.out.println(id);
		
		//Get book
//		String getResponse = given()
//		.queryParam("ID", id)
//		.header("Content-Type", "application/json")
//		.when()
//		.get("/Library/GetBook.php")
//		.then().log().all()
//		.assertThat().statusCode(200).extract().response().asString();
//		
//		System.out.println(getResponse);
//		
//		
//		//Delete book
//		String dltResponse = given()
//		.header("Content-Type", "application/json")
//		.body(payload.dltBook(id))
//		.when()
//		.delete("/Library/DeleteBook.php")
//		.then().log().all()
//		.assertThat().statusCode(200).extract().response().asString();
//		System.out.println("Deleted Record: "+dltResponse);
	}
	
	@DataProvider(name = "BooksData")

	public static Object[][] getData() {

		return new Object[][] { { "bcssas", "221112" }, { "bscss", "212112" }, { "bcass", "222112" } };

	}

}
