package api.endpoints;

import static io.restassured.RestAssured.given;

import api.client.RequestSpecFactory;
import api.model.BookShowRequest;
import api.model.PaymentRequest;
import api.response.ApiResponse;
import constants.AppConstants;
import io.restassured.response.Response;

public class BookClient {

    public ApiResponse bookShow(BookShowRequest request) {

        Response response = given()
                .spec(RequestSpecFactory.getAuthSpec(AppConstants.ROLE_USER))
                .body(request)
        .when()
                .post(AppConstants.BOOK_SHOW);

        return new ApiResponse(response);
    }

    public ApiResponse makePayment(PaymentRequest request) {

        Response response = given()
                .spec(RequestSpecFactory.getAuthSpec(AppConstants.ROLE_USER))
                .body(request)
        .when()
                .post(AppConstants.MAKE_PAYMENT);

        return new ApiResponse(response);
    }

    public ApiResponse getAllBookings() {

        Response response = given()
                .spec(RequestSpecFactory.getAuthSpec(AppConstants.ROLE_USER))
        .when()
                .get(AppConstants.GET_ALL_BOOKINGS);

        return new ApiResponse(response);
    }
}