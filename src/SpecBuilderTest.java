import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import POJO.AddBook;
import static io.restassured.RestAssured.given;

public class SpecBuilderTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		AddBook p = new AddBook();
		p.setName("Learn Appium Automation with Mahum");
		p.setIsbn("bcde");
		p.setAisle("29262");
		p.setAuthor("John foer");
		
		//req
		RequestSpecification req = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123")
				.setContentType(ContentType.JSON)
				.build();
		//resp
		ResponseSpecification resp = new ResponseSpecBuilder()
				.expectStatusCode(200)
				.expectContentType(ContentType.JSON)
				.build();
		
		//Add Book
		given().spec(req).body(p)
		.when()
		.post("/Library/Addbook.php")
		.then()
		.spec(resp).log().all();
		
		

	}

}
