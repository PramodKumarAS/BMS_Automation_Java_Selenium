package api.endpoints;

import static io.restassured.RestAssured.given;

import api.client.RequestSpecFactory;
import api.model.AddMovieRequest;
import api.response.ApiResponse;
import constants.AppConstants;
import io.restassured.response.Response;

public class MovieClient {

    public ApiResponse addMovie(AddMovieRequest request) {

        Response response = given()
                .spec(RequestSpecFactory.getAuthSpec(AppConstants.ROLE_ADMIN))
                .body(request)
        .when()
                .post(AppConstants.ADD_MOVIE);

        return new ApiResponse(response);
    }

    public ApiResponse updateMovie(AddMovieRequest request) {

        Response response = given()
                .spec(RequestSpecFactory.getAuthSpec(AppConstants.ROLE_ADMIN))
                .body(request)   // must include movieId
        .when()
                .post(AppConstants.UPDATE_MOVIE);

        return new ApiResponse(response);
    }

    public ApiResponse deleteMovie(String id) {

        AddMovieRequest request = new AddMovieRequest();
        request.setId(id);

        Response response = given()
                .spec(RequestSpecFactory.getAuthSpec(AppConstants.ROLE_ADMIN))
                .body(request)
        .when()
                .post(AppConstants.DELETE_MOVIE);

        return new ApiResponse(response);
    }

    public ApiResponse getAllMovies() {

        Response response = given()
                .spec(RequestSpecFactory.getAuthSpec(AppConstants.ROLE_ADMIN))
        .when()
                .get(AppConstants.GET_ALL_MOVIES);

        return new ApiResponse(response);
    }

    public ApiResponse getMovieById(String id) {

        Response response = given()
                .spec(RequestSpecFactory.getAuthSpec(AppConstants.ROLE_ADMIN))
        .when()
                .get(AppConstants.GET_MOVIE_BY_ID + id);

        return new ApiResponse(response);
    }
}