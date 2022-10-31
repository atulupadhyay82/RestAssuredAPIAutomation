package com.example.test;

import com.example.model.EmployeeDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

public class TestEmployee {

    @Test
    public void createEmployee() {

        // Create an object of POJO class
        EmployeeDetails emp = new EmployeeDetails();
        emp.setName("Vibha");
        emp.setSalary(75000);
        emp.setAge(30);

        // Converting a Java class object to a JSON payload as string
        ObjectMapper mapper = new ObjectMapper();
        String employeePrettyJson = null;
        try {
            employeePrettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(emp);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("Request");
        System.out.println(employeePrettyJson);
        System.out.println("=========================================");
        System.out.println("Response");

        // GIVEN
        RestAssured.given().baseUri("http://dummy.restapiexample.com/api").contentType(ContentType.JSON).body(emp)

                // WHEN
                .when().post("/v1/create")

                // THEN
                .then().assertThat().statusCode(200).body("data.name", equalTo("Vibha"))
                .body("message", equalTo("Successfully! Record has been added.")).log().body();

    }
}
