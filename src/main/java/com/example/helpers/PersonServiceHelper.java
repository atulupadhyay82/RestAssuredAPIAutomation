package com.example.helpers;

import com.example.constants.Endpoints;
import com.example.model.Person;
import com.example.utils.ConfigManager;
import com.fasterxml.jackson.core.type.TypeReference;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;

import java.lang.reflect.Type;
import java.util.List;

/**
 * API or Application Programming Interface is a type of software interface that connects different software with minimal coding.
 * Most organizations use APIs as it acts as a bridge between services and products to interact with one another with minimal hassle.
 * an API acts as an intermediary that transfers data among systems.
 * Generally, API requests are processed from client applications to web servers to authenticate and retrieve information
 * We are performing every CRUD operation here on Person class and integration scenarios
 * https://blog.testproject.io/2021/07/28/rest-api-automation-from-scratch/
 * Looking at the test pyramid, it can be observed that the first layer, i.e., unit/component test layer covers 70% of
 * the code.
 * The second layer — API or service tests layer — aims to focus on business rules of
 * applications under test and covers 20% of the code. Testing the second layer is to ensure whether
 * all the implemented functions are working accurately.
 * The final layer or the workflow test part is performed by UI, which is responsible for ensuring the accuracy of its built features. Since
 * UI testing often changes due to the ever-changing needs of presenting information, it is prone to
 * changes. As a result, testing professionals focus less on this section.
 * Often Agile and DevOps teams prefer API Testing over UI automation testing
 * because it expedites the testing workflows; what would take a few hours in GUI testing can be performed in a few minutes with API Testing.
 */
public class PersonServiceHelper {

    //We need to read the config variables
    //Rest assured about the URL, port
    //Make a GET requests on this URL and send the data back to TestGetPerson

    private static final String BASE_URL= ConfigManager.getInstance().getString("base_url");
    private static final String PORT= ConfigManager.getInstance().getString("port");

    public PersonServiceHelper(){
        RestAssured.baseURI=BASE_URL;
        RestAssured.port= Integer.parseInt(PORT);
        RestAssured.useRelaxedHTTPSValidation(); //when we are using localhost to run the API
    }

    /**
     * GET operation to get all Person
     * @return
     */

    public List<Person> getAllPerson(){
        Response response= RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .get(Endpoints.GET_ALL_PERSON)
                .andReturn();

        //To map the response in the JSon to POJO class
        Type type= new TypeReference<List<Person>>(){}.getType();

        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Response status is not 200");

        List<Person> personList=response.as(type);
        return personList;
    }

    /**
     * Post call to create new resource
     */
    public Response createPerson(Person person){
        Response response= RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(person)
                .post(Endpoints.CREATE_PERSON)
                .andReturn();
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_CREATED, "Response status is not 201");
        return response;
    }

    /**
     * Put call to update a existing resource
     */
    public Response updatePerson(int id, Person person){
        Response response= RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("id",id)
                .when()
                .body(person)
                .patch(Endpoints.UPDATE_PERSON)
                .andReturn();
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Response status is not 200");
        return response;
    }

    /**
     * Delete call to update a existing resource
     */
    public Response deletePerson(int id){
        Response response= RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("id",id)
                .log().all()
                .when()
                .patch(Endpoints.DELETE_PERSON)
                .andReturn();
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Response status is not 200");
        return response;
    }
}
