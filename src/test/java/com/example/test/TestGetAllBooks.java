package com.example.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestGetAllBooks {

    @BeforeTest
    public void setup(){
        // Specify the base URL to the RESTful web service
        RestAssured.baseURI="https://demoqa.com/BookStore/v1/Books";
        //You may for example run into a SSLPeerUnverifiedException
        // if the server is using an invalid certificate. The easiest way to workaround this is to use "relaxed HTTPs validation".
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    public void GetBooksDetails() {
        // Get the RequestSpecification of the request to be sent to the server.
        RequestSpecification httpRequest = RestAssured.given();
        // specify the method type (GET) and the parameters if any.
        //In this case the request does not take any parameters
        Response response = httpRequest.request(Method.GET, "");
        // Print the status and message body of the response received from the server
        System.out.println("Status received => " + response.getStatusLine());
        System.out.println("Response=>" + response.prettyPrint());

    }

  @Test
  public void ValidateBookHeaders() {
    RequestSpecification httpRequest = RestAssured.given();
    Response response = httpRequest.get("");
    // headers() : returns Headers
    // getHeader(): returns a Header
    // getHeaders(): returns Headers
    // Access header with a given name. Header = Content-Type
    String contentType = response.header("Content-Type");
    Assert.assertEquals(
        contentType /* actual value */, "application/json; charset=utf-8" /* expected value */);
    // Access header with a given name. Header = Server
    String serverType = response.header("Server");
    Assert.assertEquals(
        serverType /* actual value */, "nginx/1.17.10 (Ubuntu)" /* expected value */);
    }

    @Test
    public void VerifyCityInJsonResponse()
    {
        RestAssured.baseURI = "https://restapi.demoqa.com/utilities/weather/city";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/Hyderabad");

        // First get the JsonPath object instance from the Response interface
        JsonPath jsonPathEvaluator = response.jsonPath();

        // Then simply query the JsonPath object to get a String value of the node
        // specified by JsonPath: City (Note: You should not put $. in the Java code)
        String city = jsonPathEvaluator.get("City");

        // Let us print the city variable to see what we got
        System.out.println("City received from Response " + city);

        // Validate the response
        Assert.assertEquals(city, "Hyderabad", "Correct city name received in the Response");
    }

  @Test
  public void UserRegistrationSuccessful() {
    RestAssured.baseURI = "https://demoqa.com/Account/v1";
      // Create an object to ObjectMapper
      ObjectMapper objectMapper = new ObjectMapper();

      // Creating Node that maps to JSON Object structures in JSON content
      ObjectNode bookingDetails = objectMapper.createObjectNode();

      // It is similar to map put method. put method is overloaded to accept different types of data
      // String as field value
      bookingDetails.put("firstname", "Jim");
      bookingDetails.put("lastname", "Brown");
      // integer as field value
      bookingDetails.put("totalprice", 111);
      // boolean as field value
      bookingDetails.put("depositpaid", true);
      bookingDetails.put("additionalneeds", "Breakfast");
      // Duplicate field name. Will override value
      bookingDetails.put("additionalneeds", "Lunch");

      // Since requirement is to create a nested JSON Object
      ObjectNode bookingDateDetails = objectMapper.createObjectNode();
      bookingDateDetails.put("checkin", "2021-07-01");
      bookingDateDetails.put("checkout", "2021-07-01");

      // Since 2.4 , put(String fieldName, JsonNode value) is deprecated. So use either set(String fieldName, JsonNode value) or replace(String fieldName, JsonNode value)
      bookingDetails.set("bookingdates", bookingDateDetails);


      //GIVEN
      RestAssured
              .given()
              .baseUri("https://restful-booker.herokuapp.com/booking")
              .contentType(ContentType.JSON)
              // Pass JSON pay load directly
              .body(bookingDetails)
              .log()
              .all()
              // WHEN
              .when()
              .post()
              // THEN
              .then()
              .assertThat()
              .statusCode(200)
              .log()
              .all();
}


    }
