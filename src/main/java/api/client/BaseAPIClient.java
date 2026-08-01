package api.client;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class BaseAPIClient {

    public Response post(RequestSpecification spec, String endpoint, Object body){
            return given()
                .spec(spec)
                .body(body)
                    .when()
                .post(endpoint);
    }

    public Response get(RequestSpecification spec,String endpoint){
           return given()
                   .spec(spec)
                   .when()
                   .get(endpoint);

    }
}
