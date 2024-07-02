import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
public class OAuth {

	public OAuth() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//Generate access token via client credentials
		String response = given()
		.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type", "client_credentials")
		.formParam("scope", "trust")
		.when()
		.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
		.then()
		.log().all().extract().response().asString();
		
	
		JsonPath js = new JsonPath(response);
		String accesstoken = js.getString("access_token");
		System.out.println(accesstoken);
		
		//Get the access token and pass it through GET call
		given()
		.queryParam("access_token", accesstoken)
		.when()
		.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
		.then()
		.log().all();
		
		
		
		
		
	}

}
