package specBuilders;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import serialisePojo.AddPlace;
import serialisePojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuilderTest {

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

        //Request
        RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();
        RequestSpecification request = given().log().all().spec(requestSpecification).body(addPlace);

        //Response
        ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(200)
                .expectContentType(ContentType.JSON).build();
        Response response = request.when().post("/maps/api/place/add/json").then().log().all().spec(responseSpecification).extract().response();

        String responseString = response.asString();
        System.out.println(responseString);
    }

}
