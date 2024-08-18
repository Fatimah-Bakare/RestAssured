package pojo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OAuthTestPojo {

    public static void main(String[] args) {

        String[] courseTitle = {"Selenium Webdriver Java", "Cypress", "Protractor"};

        RestAssured.baseURI = "https://rahulshettyacademy.com/";

        String response = given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParam("grant_type", "client_credentials")
                .formParam("scope", "trust").when().post("oauthapi/oauth2/resourceOwner/token").then().log().all().assertThat()
                .statusCode(200).extract().response().asString();

        JsonPath js = new JsonPath(response);
        String access_token = js.getString("access_token");
        System.out.println(access_token);

        //Get Course Details
        GetCourse gc = given().log().all().queryParam("access_token", access_token).when().get("oauthapi/getCourseDetails")
                .as(GetCourse.class);

        System.out.println(gc.getLinkedIn());
        System.out.println(gc.getInstructor());

        System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());

        List<Api> apiCourses = gc.getCourses().getApi();

        for(int i = 0; i < apiCourses.size(); i++)
        {
            if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
            {
                System.out.println(apiCourses.get(i).getPrice());
            }
        }

        //Get the course names of web automation
//        List<WebAutomation> webAutomationCourses = gc.getCourses().getWebAutomation();
//
//        for(int i = 0; i < webAutomationCourses.size(); i++)
//        {
//            System.out.println(webAutomationCourses.get(i).getCourseTitle());
//        }

        ArrayList<String> a = new ArrayList<>();
        List<WebAutomation> webAutomationCourses = gc.getCourses().getWebAutomation();

        for(int i = 0; i < webAutomationCourses.size(); i++)
        {
            a.add(webAutomationCourses.get(i).getCourseTitle());
        }

        List<String> expectedList = Arrays.asList(courseTitle);
        Assert.assertTrue(a.equals(expectedList));

    }

}
