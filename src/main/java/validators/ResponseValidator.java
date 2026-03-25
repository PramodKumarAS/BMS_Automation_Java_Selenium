package validators;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ResponseValidator {
	public static ValidatableResponse validateSchema(Response response,String schemaPathJson) {
		return response.then()
				.assertThat()
				.body(matchesJsonSchemaInClasspath(schemaPathJson));
	}
}
