package api.endpoints;

import static io.restassured.RestAssured.given;

import api.client.RequestSpecFactory;
import api.model.AddTheatreRequest;
import api.response.ApiResponse;
import constants.AppConstants;
import io.restassured.response.Response;

public class TheatreClient {

	 public ApiResponse addTheatre(AddTheatreRequest request) {

	        Response response = given()
	                .spec(RequestSpecFactory.getAuthSpec(AppConstants.ROLE_PARTNER))
	                .body(request)
	        .when()
	                .post(AppConstants.ADD_THEATRE);
	        
	        return new ApiResponse(response);
	    }

    public ApiResponse getAllTheatres() {

        Response response = given()
                .spec(RequestSpecFactory.getAuthSpec(AppConstants.ROLE_PARTNER))
        .when()
                .get(AppConstants.GET_ALL_THEATRE);

        return new ApiResponse(response);
    }

    public ApiResponse getTheatresByOwnerId(String ownerId) {

        Response response = given()
                .spec(RequestSpecFactory.getAuthSpec(AppConstants.ROLE_PARTNER))
        .when()
                .get(AppConstants.GET_THEATRE_BY_OWNER + ownerId);

        return new ApiResponse(response);
    }

    public ApiResponse updateTheatre(AddTheatreRequest request) {

        Response response = given()
                .spec(RequestSpecFactory.getAuthSpec(AppConstants.ROLE_PARTNER))
                .body(request)   // request must contain _id
        .when()
                .post(AppConstants.UPDATE_THEATRE);

        return new ApiResponse(response);
    }

    public ApiResponse deleteTheatre(String id) {

        AddTheatreRequest request = new AddTheatreRequest();
        request.setId(id);

        Response response = given()
                .spec(RequestSpecFactory.getAuthSpec(AppConstants.ROLE_PARTNER))
                .body(request)
        .when()
                .post(AppConstants.DELETE_THEATRE);

        return new ApiResponse(response);
    }
}