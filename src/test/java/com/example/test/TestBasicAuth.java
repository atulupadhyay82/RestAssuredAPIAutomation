package com.example.test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

/**
 * While going through the previous tutorials you must have noticed that we have used the username and the password
 * (authentication credentials) for certain APIs. This credential setting is to enforce access control for the web resources
 * and is generally passed in the header field of an HTTP request. The implementation of basic authentication is to ensure
 * that the APIs are secured and only the users who are authorized have the access to view them. Hence, the authentication
 * information is not encrypted or hashed but encoded as base-64
 */
public class TestBasicAuth {

    @Test
    public void testingUnauthorizedAccess() {
        RestAssured.useRelaxedHTTPSValidation();
        RequestSpecification httpRequest = RestAssured.given();
        Response res = httpRequest.get("https://postman-echo.com/basic-auth");
        ResponseBody body = res.body();
        //Converting the response body to string
        String rbdy = body.asString();
        System.out.println("Data from the GET API- "+rbdy);
    }

    /**
     *  Basic Authentication in Rest Assured: the basic authentication scheme uses the username and password in base64 encoded format
     */
    @Test
    public void testingAuthorizedAccessUsingBasicAUTH() {
        RestAssured.useRelaxedHTTPSValidation();
        RequestSpecification httpRequest = RestAssured.given().auth().basic("postman", "password");
        Response res = httpRequest.get("https://postman-echo.com/basic-auth");
        ResponseBody body = res.body();
        //Converting the response body to string
        String rbdy = body.asString();
        System.out.println("Data from the GET API- "+rbdy);
    }
  /**
   * Preemptive Authentication By default, Rest Assured uses the challenge-response mechanism. This
   * means that it waits for the server to challenge rather than send the credentials directly. By
   * using the preemptive directives we can avoid that additional call that the server makes and
   * hence additional complications. In a way, it is similar to the basic auth we saw above, the
   * only difference is that an additional premptive () directive adds after auth ()
   *
   * <p>given().auth().preemptive().basic("your username", "your password").get("your end point
   * URL");
   */

  /**
   * Digest Authentication It is somewhat similar to challenge-based authentication but is more
   * secure as it uses a digestive key in subsequent requests. If at all it is intercepted by an
   * eavesdropper, he will get access only to the transaction performed and not the user password.
   * The transaction might be replayed but a new transaction cannot be made as the password is not
   * exposed. Its syntax is similar to basic authentication-
   *
   * <p>given().auth().digest("your username", "your password").get("your)
   */

  /**
   * Form Authentication There can be many cases when you need to pass the authentication
   * credentials in an HTML form. This request is generally sent as a post method where the
   * credentials entered in the form are used for authentication.
   *
   * <p>given() .auth().form("your username", "your password").post("your end point URL") If you use
   * this approach then Rest Assured will first have to parse through the HTML response to find the
   * fields for input and then send the form parameters. However, there is a high possibility that
   * this approach might fail if the webpage is complex. Additionally, it would also fail if the
   * context path is not included in the action attribute of the service. To optimize it to handle
   * such cases, you may use the below format where you explicitly pass the required fields by
   * providing the FormAuthConfig()-
   *
   * <p>given().auth().form("your username", "your password", new
   * FormAuthConfig("/perform_signIn","user","password"))
   */

  /**
   * OAuth Authentication Another type of authentication is OAuth authentication. OAuth is an
   * authorization framework that defines an identity protocol. It has wide usage in web
   * applications and there are high chances that you will have to automate those authentication
   * actions. These can be of two types viz, OAuth 1.0 and OAuth 2.0 which we will discuss now
   *
   * <p>OAuth 1.0 Secured resources built using OAuth 1.0 requires passing consumer key, secret,
   * access token, and token secret. The syntax it follows is -
   *
   * <p>given().auth().oauth(consumerKey, consumerSecret, accessToken, tokenSecret).get("your end
   * point URL")
   *
   * <p>OAuth 2.0 There are cases when we need to generate an access token for a user session. This
   * access token performs various transactions and helps maintain the user session. While using
   * OAuth 2.0 you need to directly pass the access token generated when the user login using the
   * below syntax-
   *
   * <p>given().auth().oauth2("Access token").get("your end point URL")
   */
}
