package basics;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import methods.Payload;
import methods.ReUsableMethods;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {

    @Test(dataProvider = "BookData")
    public void addBook(String isbn, String aisle) {
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().log().all().header("Content-Type", "application/json").body(Payload.addBook(isbn, aisle))
                .when().post("/Library/Addbook.php").then().log().all().assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath js = ReUsableMethods.rawToJson(response);
        String id = js.get("ID");
        System.out.println(id);

        // Call deleteBook method after adding the book
        deleteBook(id);
    }

    //Delete book
    public void deleteBook(String id) {
        RestAssured.baseURI = "http://216.10.245.166";
        String deleteResponse = given().log().all().header("Content-Type", "application/json")
                .body(Payload.deleteBook(id))
                .when().post("/Library/DeleteBook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();

        System.out.println("Delete Book Response: " + deleteResponse);

        try {
            JsonPath js = new JsonPath(deleteResponse);
            String message = js.get("msg");
            System.out.println("Delete response message: " + message);
        } catch (Exception e) {
            System.err.println("Failed to parse the JSON response: " + e.getMessage());
        }
    }

    @DataProvider(name="BookData")
    public Object[][] getData() {
        //array -> is a collection of element
        //multidimensional array -> is a collection of arrays
        return new Object[][] {{"cgs", "7863"}, {"hsh", "6546"}, {"gsy", "7684"}};
    }

}
