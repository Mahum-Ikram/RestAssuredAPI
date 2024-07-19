import io.restassured.path.json.JsonPath;
import junit.framework.Assert;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import POJO.API;
import POJO.GetCourse;
import POJO.WebAutomation;
public class OAuth {

	public OAuth() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] courseTitles = {"Selenium Webdriver Java","Cypress","Protractor"};
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
		GetCourse gc = given()
		.queryParams("access_token", accesstoken)
		.when().log().all()
		.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
		.as(GetCourse.class);
		
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());

		List<API> apiCourses = gc.getCourses().getApi();
		for (int i = 0; i < apiCourses.size(); i++) {
			if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(apiCourses.get(i).getCourseTitle());

			}
		}

		// print all the course titles of webautomation

		List<WebAutomation> webAutomationCourses = gc.getCourses().getWebAutomation();
		for (int i = 0; i < webAutomationCourses.size(); i++) {
			System.out.println(webAutomationCourses.get(i).getCourseTitle());

		}

		// Get the course names of WebAutomation
		ArrayList<String> a = new ArrayList<String>();

		List<POJO.WebAutomation> w = gc.getCourses().getWebAutomation();

		for (int j = 0; j < w.size(); j++) {
			a.add(w.get(j).getCourseTitle());
		}

		List<String> expectedList = Arrays.asList(courseTitles);

		Assert.assertTrue(a.equals(expectedList));
				
				
		
	}

}
