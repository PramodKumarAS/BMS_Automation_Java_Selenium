package api.endpoints;

import static io.restassured.RestAssured.given;

import api.client.RequestSpecFactory;
import api.model.AddShowRequest;
import api.model.GetAllShowsByTheatreRequest;
import api.response.ApiResponse;
import constants.AppConstants;
import io.restassured.response.Response;

public class ShowClient {

    public ApiResponse addShow(AddShowRequest request) {

        Response response = given()
                .spec(RequestSpecFactory.getAuthSpec(AppConstants.ROLE_PARTNER))
                .body(request)
        .when()
                .post(AppConstants.ADD_SHOW);

        return new ApiResponse(response);
    }

    public ApiResponse updateShow(AddShowRequest request) {

        Response response = given()
                .spec(RequestSpecFactory.getAuthSpec(AppConstants.ROLE_PARTNER))
                .body(request) // must include showId
        .when()
                .post(AppConstants.UPDATE_SHOW);

        return new ApiResponse(response);
    }

    public ApiResponse deleteShow(String id) {

        AddShowRequest req = new AddShowRequest();
        req.setShowId(id);

        Response response = given()
                .spec(RequestSpecFactory.getAuthSpec(AppConstants.ROLE_PARTNER))
                .body(req)
        .when()
                .post(AppConstants.DELETE_SHOW);

        return new ApiResponse(response);
    }

    public ApiResponse getAllShowsByTheatre(String theatreId) {

    	GetAllShowsByTheatreRequest req = new GetAllShowsByTheatreRequest();
        req.setTheatreId(theatreId);

        Response response = given()
                .spec(RequestSpecFactory.getAuthSpec(AppConstants.ROLE_PARTNER))
                .body(req)
        .when()
                .post(AppConstants.GET_SHOWS_BY_THEATRE);
        
        return new ApiResponse(response);
    }

    public ApiResponse getAllTheatresByMovie(String movieId) {

        AddShowRequest req = new AddShowRequest();
        req.setMovie(movieId);

        Response response = given()
                .spec(RequestSpecFactory.getAuthSpec(AppConstants.ROLE_PARTNER))
                .body(req)
        .when()
                .post(AppConstants.GET_THEATRES_BY_MOVIE);

        return new ApiResponse(response);
    }

    public ApiResponse getShowById(String showId) {

        AddShowRequest req = new AddShowRequest();
        req.setShowId(showId);

        Response response = given()
                .spec(RequestSpecFactory.getAuthSpec(AppConstants.ROLE_PARTNER))
                .body(req)
        .when()
                .post(AppConstants.GET_SHOW_BY_ID);

        return new ApiResponse(response);
    }
}