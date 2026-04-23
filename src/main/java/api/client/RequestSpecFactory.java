package api.client;

import api.auth.AuthManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecFactory {

    // Base spec (no auth)
    public static RequestSpecification getBaseSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)      
                .addFilter(ErrorLoggingFilter.logErrorsTo(System.err))  // prints only on failure
                .build();
    }

    // Auth spec (adds token automatically)
    public static RequestSpecification getAuthSpec(String role) {
    	
        return new RequestSpecBuilder()
                .addRequestSpecification(getBaseSpec())
                .addHeader("Authorization", "Bearer " + AuthManager.getToken(role))
                .build();
    }
}