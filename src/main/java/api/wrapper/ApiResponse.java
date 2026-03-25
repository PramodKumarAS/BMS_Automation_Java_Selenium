package api.wrapper;

import io.restassured.response.Response;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ApiResponse {

	private Response response;
	
	public ApiResponse(Response response) {
		this.response=response;
	}
	
	public ApiResponse assertStatus(int statusCode) {
		response.then().statusCode(statusCode);
		
		return this;
	}
	
    public ApiResponse validateSchema(String schemaPath) {
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath(schemaPath));
        return this;
    }

    public <T> T as(Class<T> clazz) {
        return response.as(clazz);
    }	
}