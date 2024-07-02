import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class BugTest {

	public static void main(String[] args) {
		
		RestAssured.baseURI = "https://mahumikram.atlassian.net/";
		
		//create issue
		String response = given()
		.header("Content-Type", "application/json")
		.header("Authorization", "Basic bWFodW0uaWtyYW1AZ21haWwuY29tOkFUQVRUM3hGZkdGMDFVbjZCeHBoR1kzV2lEWHpxWGFmZHhpa09JYXVKcFVvX0xGMjBKU1diM2FJUTl3UFphaU5zemsxUkxQZ2hKV3d5aFYxSldncll3V0pvMHcxclNzdkd0SlJIX0NnbXB2MlRxTGRFOEw4akxkLWo5VXdHVDhnVm5XSkoybWxRa3B4YVhkM19mc2prb2paRGFhdFBOeHA5R0RvaktIT2hSZ3JjN3NmVnlMZTdyQT04MTYwOUEzMQ==")
		.body("{\r\n"
				+ "    \"fields\": {\r\n"
				+ "       \"project\":\r\n"
				+ "       {\r\n"
				+ "          \"key\": \"MP\"\r\n"
				+ "       },\r\n"
				+ "       \"summary\": \"Dropdowns are not working properly\",\r\n"
				+ "       \"issuetype\": {\r\n"
				+ "          \"name\": \"Bug\"\r\n"
				+ "       }\r\n"
				+ "   }\r\n"
				+ "}\r\n"
				+ "")
		.when()
		.post("rest/api/2/issue")
		.then()
		.assertThat()
		.statusCode(201).log().all().extract().response().asString();
		
		JsonPath js = new JsonPath(response); //for parsing Json
    	
    	String ID = js.getString("id");
		System.out.println("New bug has been created " + ID);

		//add attachment
		given()
		.pathParam("key", ID)
		.header("X-Atlassian-Token", "no-check")
		.header("Authorization", "Basic bWFodW0uaWtyYW1AZ21haWwuY29tOkFUQVRUM3hGZkdGMDFVbjZCeHBoR1kzV2lEWHpxWGFmZHhpa09JYXVKcFVvX0xGMjBKU1diM2FJUTl3UFphaU5zemsxUkxQZ2hKV3d5aFYxSldncll3V0pvMHcxclNzdkd0SlJIX0NnbXB2MlRxTGRFOEw4akxkLWo5VXdHVDhnVm5XSkoybWxRa3B4YVhkM19mc2prb2paRGFhdFBOeHA5R0RvaktIT2hSZ3JjN3NmVnlMZTdyQT04MTYwOUEzMQ==")
		
		//make a file object and pass the path
		.multiPart("file", new File("C:\\Users\\mahum.ikram\\Desktop\\Images and Videos\\7-Common-Types-of-Software-Testing@1x-1280x720.png")).log().all()
		.when()
		.post("rest/api/2/issue/{key}/attachments")
		.then()
		.log().all();
		
		//get issue details
		given()
		.header("Content-Type", "application/json")
		.header("Authorization", "Basic bWFodW0uaWtyYW1AZ21haWwuY29tOkFUQVRUM3hGZkdGMDFVbjZCeHBoR1kzV2lEWHpxWGFmZHhpa09JYXVKcFVvX0xGMjBKU1diM2FJUTl3UFphaU5zemsxUkxQZ2hKV3d5aFYxSldncll3V0pvMHcxclNzdkd0SlJIX0NnbXB2MlRxTGRFOEw4akxkLWo5VXdHVDhnVm5XSkoybWxRa3B4YVhkM19mc2prb2paRGFhdFBOeHA5R0RvaktIT2hSZ3JjN3NmVnlMZTdyQT04MTYwOUEzMQ==")
		.pathParam("ID", ID)
		.when()
		.get("/rest/api/2/issue/{ID}")
		.then()
		.log().all();
		

		

	}

}
