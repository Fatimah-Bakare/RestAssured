package basics;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.io.File;

import static io.restassured.RestAssured.given;

public class JiraBugTest {

    public static void main(String[] args) {

        RestAssured.baseURI = "https://fatimahabakare.atlassian.net/";

        String createIssueResponse = given().log().all().header("Content-Type", "application/json").header("Authorization", "Basic ZmF0aW1haC5hLmJha2FyZUBnbWFpbC5jb206QVRBVFQzeEZmR0Ywcm95TVNoVkQ4czhkbnRfVDEzUmV2QmpFUkxON1pNNWY2RGduUm1pY0pYaElpQ0YwamdKQmpDeXFRN2Z3YW5QNkRlRHZqb2NkUlplbElfRVE1SXd6M25GMlcwNEZzeElQUEluT2JPMm5KRlFkcUJRR1FpM0dJMFJVWlBCa21FX0VRWWNlRHpHQ3h1M0piOGkxTEp1RWdWQWIxdERRREFJazNSbjZIMVpEeE0wPUU2Q0YxOTMw")
                .body("{\n" +
                        "    \"fields\": {\n" +
                        "       \"project\":\n" +
                        "       {\n" +
                        "          \"key\": \"RSJIR\"\n" +
                        "       },\n" +
                        "       \"summary\": \"Search is not working - Automation Rest Assured.\",\n" +
                        "       \"description\": \"Creating of an issue using project keys and issue type names using the REST API\",\n" +
                        "       \"issuetype\": {\n" +
                        "          \"name\": \"Bug\"\n" +
                        "       }\n" +
                        "   }\n" +
                        "}\n")
                .when().post("rest/api/2/issue").then().log().all().assertThat().statusCode(201).extract().response().asString();

        JsonPath js = new JsonPath(createIssueResponse);
        String issueId = js.getString("id");
        System.out.println(issueId);

        //Add attachment (path parameter)
        given().pathParams("key", issueId).header("X-Atlassian-Token", "no-check")
                .header("Authorization", "Basic ZmF0aW1haC5hLmJha2FyZUBnbWFpbC5jb206QVRBVFQzeEZmR0Ywcm95TVNoVkQ4czhkbnRfVDEzUmV2QmpFUkxON1pNNWY2RGduUm1pY0pYaElpQ0YwamdKQmpDeXFRN2Z3YW5QNkRlRHZqb2NkUlplbElfRVE1SXd6M25GMlcwNEZzeElQUEluT2JPMm5KRlFkcUJRR1FpM0dJMFJVWlBCa21FX0VRWWNlRHpHQ3h1M0piOGkxTEp1RWdWQWIxdERRREFJazNSbjZIMVpEeE0wPUU2Q0YxOTMw")
                .multiPart("file", new File("C:\\Users\\fatim\\Downloads\\UberUML_Diagram.png"))
                .when().post("rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);

        //Get attachment
        given().pathParams("key", issueId).header("Authorization", "Basic ZmF0aW1haC5hLmJha2FyZUBnbWFpbC5jb206QVRBVFQzeEZmR0Ywcm95TVNoVkQ4czhkbnRfVDEzUmV2QmpFUkxON1pNNWY2RGduUm1pY0pYaElpQ0YwamdKQmpDeXFRN2Z3YW5QNkRlRHZqb2NkUlplbElfRVE1SXd6M25GMlcwNEZzeElQUEluT2JPMm5KRlFkcUJRR1FpM0dJMFJVWlBCa21FX0VRWWNlRHpHQ3h1M0piOGkxTEp1RWdWQWIxdERRREFJazNSbjZIMVpEeE0wPUU2Q0YxOTMw")
                .when().get("rest/api/2/issue/{key}").then().log().all().assertThat().statusCode(200);
    }

}
