package post_put_delete;

import org.json.JSONObject;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.restassured.http.ContentType;

import java.io.File;

public class Post_Put_Delete {

    @Test
    public void post_request(){
        File file = new File("resources/create_employee.json");

        int id = given()
                .baseUri("https://dummy.restapiexample.com/api/v1")
                .contentType(ContentType.JSON)
                .body(file).
        when()
                .post("/create")
        .then()
                .log().all()
                .statusCode(200)
                .body("data.name",equalTo("Fran"))
                .extract().path("data.id");

        System.out.println(id);
    }

    @Test
    public void post_request_using_json_object(){
        JSONObject body = new JSONObject();
        body.put("name","Fran");
        body.put("salary","1700");
        body.put("age","27");

        int id = given()
                .baseUri("https://dummy.restapiexample.com/api/v1")
                .contentType(ContentType.JSON)
                .body(body.toString()).
                        when()
                .post("/create")
                .then()
                .statusCode(200)
                .body("data.name",equalTo("Fran"))
                .extract().path("data.id");

        System.out.println(id);
    }

    @Test
    public void put_request_using_json_object() {

        JSONObject body = new JSONObject();
        body.put("name", "Frodo");
        body.put("salary", "1100");
        body.put("age", "24");

        given()
                .baseUri("http://dummy.restapiexample.com/api/v1")
                .contentType(ContentType.JSON)
                .body(body.toString()).
                when()
                .put("/update/24").
                then()
                .statusCode(200);
    }

    @Test
    public void delete_request() {

        String msg = given()
                .baseUri("http://dummy.restapiexample.com/api/v1")
                .contentType(ContentType.JSON).
                        when()
                .delete("/delete/2").
                        then()
                .statusCode(200)
                .extract().path("message");

        System.out.println(msg);
    }
}
