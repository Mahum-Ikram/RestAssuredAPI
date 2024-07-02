package file;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {

    @Test(dataProvider = "BooksData")
    public void addBook(String isbn, String aisle) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        // Add book
        String response = given().header("Content-Type", "application/json")
                .body(payload.addBook(isbn, aisle)) // Ensure payload.addBook returns JSON
                .when()
                .post("/Library/Addbook.php")
                .then()
                .log().all().statusCode(200)
                .extract().response().asString();

        System.out.println("Add Book Response: " + response);
        JsonPath js = new JsonPath(response);
        String id = js.get("ID");
        System.out.println("Book ID: " + id);

        // Call delete method with the generated book ID
        dltBook(id);
    }

   
    public void dltBook(String id) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";

        // Delete book
        String response = given().header("Content-Type", "application/json")
                .body(payload.dltBook(id)) // Ensure payload.dltBook uses the correct id
                .when()
                .post("/Library/DeleteBook.php") // Assuming this is the correct endpoint
                .then()
                .log().all().statusCode(200)
                .extract().response().asString();

        System.out.println("Delete Book Response: " + response);
        JsonPath js = new JsonPath(response);
        String message = js.get("msg");
        System.out.println("Delete Message: " + message);
    }

    @DataProvider(name = "BooksData")
    public static Object[][] getData() {
        return new Object[][]{
                {"bcesssas", "2212112"},
                {"bssecss", "2122112"},
                {"bcaesss", "2222112"}
        };
    }
}
