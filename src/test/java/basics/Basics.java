package basics;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import methods.Payload;
import methods.ReUsableMethods;
import org.testng.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Basics {

    public static void main(String[] args) throws IOException {

        // Validate if AddPlace API is working as expected

        //Add place -> Update Place with New Address -> Get Place to validate if New Address is present in response

        //Add place

        //given - all input details
        //when - Submit the API -> resource and http method
        //then - validate the response

        //Content of the file to String -> content of the file can convert to Byte -> Byte data to String (files.readAllBytes)

//        RestAssured.baseURI = "https://rahulshettyacademy.com";
//        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
//                .body(org.Payload.addPlace()).when().post("maps/api/place/add/json").then().log().all().assertThat()
//                .statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)")
//                .extract().response().asString();


        // Response is printed as a string, log().all() infront of then has been removed. Refer to the previous commented code

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(new String(Files.readAllBytes(Path.of("C:\\Users\\fatim\\Rest API Testing\\addPlace.json")))).when().post("maps/api/place/add/json").then().assertThat()
                .statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)")
                .extract().response().asString();

        System.out.println(response);

        JsonPath js = new JsonPath(response); //for parsing the json
        String placeId = js.getString("place_id");
        System.out.println(placeId);

        //Update place

        String newAddress = "70 winter walk, USA";

        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\""+placeId+"\",\n" +
                        "\"address\":\""+newAddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}").when().put("maps/api/place/update/json").then().assertThat().log().all().statusCode(200)
                .body("msg", equalTo("Address successfully updated"));

        //Get place

        String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
                .when().get("maps/api/place/get/json").then().assertThat().log().all().statusCode(200)
                .extract().response().asString();

        JsonPath jsII = ReUsableMethods.rawToJson(getPlaceResponse);
        String actualAddress = jsII.getString("address");
        System.out.println(actualAddress);
        Assert.assertEquals(actualAddress, newAddress);

    }

}
