import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import junit.framework.Assert;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import POJO.LoginCredentials;
import POJO.LoginResponse;
import POJO.OrderDetails;
import POJO.Orders;




public class EcommerceAPITest {

	public static void main(String[] args) {

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		//Login Request
		LoginCredentials login = new LoginCredentials();
		login.setUserEmail("postman@mailinator.com");
		login.setUserPassword("Hello@123");
		
		RequestSpecification req = new RequestSpecBuilder()
		.setContentType(ContentType.JSON)
		.build();
		
		RequestSpecification reqLogin = given().relaxedHTTPSValidation().log().all()
		.spec(req)
		.body(login);
		LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login").then().extract().response().as(LoginResponse.class);
		
		System.out.println(loginResponse.getToken());
		System.out.println(loginResponse.getUserId());
		System.out.println(loginResponse.getMessage());
		
		String token = loginResponse.getToken();
		String userID = loginResponse.getUserId();
		
		System.out.println(token);
		System.out.println(userID);
		
		//Add product	
		RequestSpecification addProduct = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", loginResponse.getToken()).build();
		
		RequestSpecification reqAddProduct = given()
		.spec(addProduct)
		.param("productAddedBy", loginResponse.getUserId())
		.param("productName", "ZARA COAT 3")
		.param("productCategory", "fashion")
		.param("productSubCategory", "shirts")
		.param("productPrice", "31500")
		.param("productFor", "Men")
		.param("productDescription", "Zara coat for Women and girls")
		.multiPart("productImage", new File("C:\\Users\\mahum.ikram\\Desktop\\Images and Videos\\2bs3ry.jpg"));
		
		String addProductResponse = reqAddProduct
				.when().log().all()
				.post("/api/ecom/product/add-product")
				.then().log().all().extract().response().asString();
		JsonPath js = new JsonPath(addProductResponse);
		String productId =js.get("productId");
		System.out.println(productId);
		
		
		//Create an order
		
		RequestSpecification createOrder = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token)
				.setContentType(ContentType.JSON).build();
		
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setCountry("Pakistan");
		orderDetails.setProductOrderedId(productId);
		
		List<OrderDetails> orderList = new ArrayList <OrderDetails>();
		orderList.add(orderDetails);
		Orders order = new Orders();
		order.setOrders(orderList);
		
		RequestSpecification createOrderReq=given().log().all().spec(createOrder).body(order);

		String responseAddOrder = createOrderReq
				.when().post("/api/ecom/order/create-order")
				.then().log().all().extract().response().asString();
				System.out.println(responseAddOrder);
				
		//Delete order
				
		RequestSpecification deleteOrder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token)
				.setContentType(ContentType.JSON).build();
		
		RequestSpecification deleteProduct = given().spec(deleteOrder).pathParam("productId", productId);
		
		
		String deleteProductResponse = deleteProduct.when().delete("/api/ecom/product/delete-product/{productId}")
		.then().log().all().extract().response().asString();
		
		JsonPath js1 = new JsonPath(deleteProductResponse);
		Assert.assertEquals("Product Deleted Successfully",js1.get("message"));

	}

}
