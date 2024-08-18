package pojoTest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import serialisePojo.AddPlace;
import serialisePojo.Location;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SerialiseTest {

    public static void main(String[] args) {

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        AddPlace addPlace = new AddPlace();
        addPlace.setAccuracy(50);
        addPlace.setAddress("29, side layout, cohen 09");
        addPlace.setLanguage("French-IN");
        addPlace.setName("Frontline house");
        addPlace.setPhoneNumber("(+91) 983 893 3937");
        addPlace.setWebsite("http://google.com");
        List<String> myList = new ArrayList<String>();
        myList.add("shoe park");
        myList.add("shop");
        addPlace.setTypes(myList);
        Location location = new Location();
        location.setLat((float) -38.383494);
        location.setLng((float) 33.427362);
        addPlace.setLocation(location);

        Response response = given().log().all().queryParam("key", "qaclick123").body(addPlace)
                .when().post("/maps/api/place/add/json").then().log().all().assertThat().statusCode(200).extract().response();

        String responseString = response.asString();
        System.out.println(responseString);
    }

}
