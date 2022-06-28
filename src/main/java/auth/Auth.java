package auth;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class Auth {
    /*
     * Authentication:
     *  1. Basic VS Digest
     *  2. Preemptive VS Challenged
     *
     *  Example
     *   URI: https://postman-echo.com
     *   End-point: /digest-auth
     */

    @Test
    public void digest_authentication() {

        given()
                .baseUri("https://postman-echo.com")
                .auth().digest("postman", "password").
                when()
                .get("/digest-auth").
                then()
                .log().all()
                .statusCode(200);
    }

    /*
     * oAuth 1.0
     *
     * Consumer Key (API Key)
     * Consumer Secret (API Secret Key)
     * Access Token
     * Access Token Secret
     *
     * https://api.twitter.com/1.1/statuses/update.json
     */

    @Test
    public void post_A_Tweet() {

        String tweet = "This is a test tweet from Rest Assured.";

        given()
                .baseUri("https://api.twitter.com/1.1")
                .auth().oauth(
                "uJ6haiHUTq0LEqpwfIaTwXlNT",
                "mJJgpLtWpmvIgyE6BazJ79UJapv3N1DaI4F1PzhVTax0T4Z5GF",
                "950475365499236352-2Vewe2vmKgzHhttyzAMXb38ohT1QjQx",
                "yIEMjAfl8vSDoAKicuWQ0hNLu0vMrk6lzMRuK3WzecMVX")
                .param("status", tweet).
                when()
                .post("/statuses/update.json").
                then()
                .log().all()
                .statusCode(200);
    }


    /*
     * oAuth 2.0
     *
     * Consumer Key (API Key)
     * Consumer Secret (API Secret Key)
     * Access Token - On the fly
     *
     */

    @Test
    public void paypal_test() {

        String accessToken = given()
                .baseUri("https://api.sandbox.paypal.com/v1")
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .header("Accept-Language","en_US")
                .param("grant_type", "client_credentials")
                .auth().preemptive().basic(
                        "Afq8Vt73m98ez_xXDGaDIwXueTXcTvGSizt4SkOcM5J9ZeWoxFKuCI4x12kqYPsZis6mCPQLeuHhiMRg",
                        "EBlgaZ_4UKOo6i22KAcQjVyo8hFYqySaRuawMrUmPlmHSbQJLjLOIRDdnKvQ3W5lnc-d6iulVsc9OR-z").
                        when()
                .post("/oauth2/token").
                        then()
                .log().all()
                .statusCode(200)
                .extract().path("access_token");

        //Generate next invoice

        given()
                .baseUri("https://api.sandbox.paypal.com/v2")
                .contentType("application/json")
                .auth().oauth2(accessToken).
                when()
                .post("/invoicing/generate-next-invoice-number").
                then()
                .log().all()
                .statusCode(200);



    }
}
