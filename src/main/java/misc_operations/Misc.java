package misc_operations;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Misc {

    @BeforeClass
    public static void setup(){
        baseURI="http://localhost";
        port=9999;
    }

    /* Specifying request port*/
    @Test
    public void specify_port(){

        when()
                .get("/whatssup")
        .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void connect_whatsupp(){

        when()
                .get("/whatssup")
        .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void validate_response_time(){

        when()
                .get("/whatssup")
                .then()
                .time(lessThan(900L), TimeUnit.MILLISECONDS)
                .statusCode(200);
    }

}
