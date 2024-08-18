package basics;

import io.restassured.path.json.JsonPath;
import methods.Payload;

public class ComplexJsonParse {

    public static void main(String[] args) {
        JsonPath js = new JsonPath(Payload.coursePrice());

        //1. Print the number of courses returned by API
        int count = js.getInt("courses.size()");
        System.out.println(count);

        //2. Print purchase amount
        int totalAmount = js.getInt("dashboard.purchaseAmount");
        System.out.println(totalAmount);

        //3. Print title of the first course
        String firstCourseTitle = js.getString("courses[0].title");
        System.out.println(firstCourseTitle);

        String secondCourseTitle = js.getString("courses[1].title");
        System.out.println(secondCourseTitle);

        //4. Print all course title and their respective prices
        for(int i = 0; i < count; i++) {
         String courseTitle = js.get("courses["+i+"].title");
            System.out.println(courseTitle);
            System.out.println(js.get("courses["+i+"].price").toString());
            System.out.println(js.get("courses["+i+"].copies").toString());
        }

        //5. Print number of copies sold by RPA course
        System.out.println("Print number of copies sold by RPA course");

        for(int i = 0; i < count; i++) {
            String courseTitle = js.get("courses["+i+"].title");
            if(courseTitle.equalsIgnoreCase("RPA")) {
                System.out.println(js.get("courses["+i+"].copies").toString());
                break;
            }
        }

        //6. Sum the total number of price for all Courses
        // refer to sumValidation class

    }

}
