package tests.api;

import java.util.Arrays;

import org.bson.Document;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mongodb.client.MongoCollection;

import api.builder.RequestBuilder;
import api.model.AddShowRequest;
import api.model.AddShowResponse;
import api.model.BookShowRequest;
import api.model.BookShowResponse;
import api.model.Booking;
import api.model.CurrentUserResponse;
import api.model.GetAllBookingsResponse;
import api.model.PaymentResponse;
import api.model.Show;
import base.APIBaseTest;
import constants.AppConstants;
import data.MongoConnection;
import data.MongoHelper;
import data.TestDataLoader;

public class BookApiTests extends APIBaseTest {

    Show                       showData               = null;
    MongoCollection<Document> mdb_ShowsCollection    = null;
    MongoCollection<Document> mdb_BookingsCollection = null;
    MongoCollection<Document> mdb_MoviesCollection   = null;
    MongoCollection<Document> mdb_TheatresCollection = null;
    String                     movieId                = null;
    String                     theatreId              = null;

    @BeforeClass
    public void setup() {
        showData       = TestDataLoader.loadShows("shows.json");

        mdb_ShowsCollection    = MongoConnection.connect(AppConstants.MONGO_DB_NAME, AppConstants.MONGO_SHOWS_COLLECTION);
        mdb_BookingsCollection = MongoConnection.connect(AppConstants.MONGO_DB_NAME, AppConstants.MONGO_BOOKINGS_COLLECTION);
        mdb_MoviesCollection   = MongoConnection.connect(AppConstants.MONGO_DB_NAME, AppConstants.MONGO_MOVIES_COLLECTION);
        mdb_TheatresCollection = MongoConnection.connect(AppConstants.MONGO_DB_NAME, AppConstants.MONGO_THEATRES_COLLECTION);

        Document mdb_Movie = MongoHelper.findOneByAnyParams(mdb_MoviesCollection, "movieName", AppConstants.SEED_MOVIE_NAME);
        movieId = mdb_Movie.getObjectId("_id").toHexString();

        Document mdb_Theatre = MongoHelper.findOneByAnyParams(mdb_TheatresCollection, "name", AppConstants.SEED_THEATRE_NAME);
        theatreId = mdb_Theatre.getObjectId("_id").toHexString();
    }

    @AfterMethod
    public void cleanup() {
        MongoHelper.deleteAll(mdb_BookingsCollection);
        MongoHelper.deleteOne(mdb_ShowsCollection, "name", showData.getName());
    }

    @Test(groups = {"@smoke"}, description = "POST /make-payment: Should return 200, pass schema validation and return payment transaction data")
    public void shouldReturnTransactionId_whenValidPaymentIsMade() {
    	
        PaymentResponse response = bookClient.makePayment(RequestBuilder.buildPaymentRequest())
                .assertStatus(200)
                .validateSchema("schema/payment-booking-schema.json")
                .as(PaymentResponse.class);

        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals(response.getMessage(), AppConstants.PAYMENT_SUCCESS_MESSAGE);
        Assert.assertNotNull(response.getData());
    }

    @Test(groups = {"@regression"}, description = "POST /book-show: Should return 200, pass schema validation and return correct booking details")
    public void shouldBookShow_whenValidPaymentAndShowDetailsAreProvided() {
        CurrentUserResponse authResponse = authClient.getCurrentUser(AppConstants.ROLE_USER)
                .assertStatus(200)
                .as(CurrentUserResponse.class);

        AddShowRequest showRequest = RequestBuilder.buildAddShowRequest(
                showData.getName(),
                showData.getDate(),
                showData.getTime(),
                movieId,
                showData.getTicketPrice(),
                showData.getTotalSeats(),
                theatreId);

        AddShowResponse showResponse = showClient.addShow(showRequest)
                .assertStatus(200)
                .as(AddShowResponse.class);

        showClient.getAllShowsByTheatre(theatreId).assertStatus(200);

        PaymentResponse paymentResponse = bookClient.makePayment(RequestBuilder.buildPaymentRequest())
                .assertStatus(200)
                .as(PaymentResponse.class);

        BookShowRequest bookRequest = RequestBuilder.buildBookShowRequest(
                Arrays.asList(10),
                showResponse.getShow().getId(),
                paymentResponse.getData(),
                authResponse.getUser().getId()
        );
        
        BookShowResponse bookResponse = bookClient.bookShow(bookRequest)
                .assertStatus(200)
                .validateSchema("schema/booking-schema.json")
                .as(BookShowResponse.class);

        Document mdb_Document = MongoHelper.findOne(mdb_BookingsCollection, "_id", bookResponse.getData().getId());

        Assert.assertTrue(bookResponse.isSuccess());
        Assert.assertEquals(bookResponse.getMessage(), AppConstants.BOOKING_SUCCESS_MESSAGE);

        Assert.assertEquals(bookResponse.getData().getShow(),          showResponse.getShow().getId());
        Assert.assertEquals(bookResponse.getData().getUser(),          authResponse.getUser().getId());
        Assert.assertEquals(bookResponse.getData().getSeats(),         mdb_Document.get("seats"));
        Assert.assertEquals(bookResponse.getData().getTransactionId(), mdb_Document.get("transactionId").toString());
        Assert.assertNotNull(bookResponse.getData().getId());
        Assert.assertNotNull(bookResponse.getData().getCreatedAt());
        Assert.assertNotNull(bookResponse.getData().getUpdatedAt());
    }

    @Test(groups = {"@regression"}, description = "GET /get-all-bookings: Should return 200, pass schema validation and include the newly created booking with correct fields")
    public void shouldReturnBookingInList_whenFetchingAllBookings() {
        CurrentUserResponse authResponse = authClient.getCurrentUser(AppConstants.ROLE_USER)
                .assertStatus(200)
                .as(CurrentUserResponse.class);

        AddShowRequest showRequest = RequestBuilder.buildAddShowRequest(
                showData.getName(),
                showData.getDate(),
                showData.getTime(),
                movieId,
                showData.getTicketPrice(),
                showData.getTotalSeats(),
                theatreId);

        AddShowResponse showResponse = showClient.addShow(showRequest)
                .assertStatus(200)
                .as(AddShowResponse.class);

        showClient.getAllShowsByTheatre(theatreId).assertStatus(200);

        PaymentResponse paymentResponse = bookClient.makePayment(RequestBuilder.buildPaymentRequest())
                .assertStatus(200)
                .as(PaymentResponse.class);

        BookShowRequest bookRequest = RequestBuilder.buildBookShowRequest(
                Arrays.asList(10),
                showResponse.getShow().getId(),
                paymentResponse.getData(),
                authResponse.getUser().getId()
        );
        
        bookClient.bookShow(bookRequest).assertStatus(200);

        GetAllBookingsResponse getAllResponse = bookClient.getAllBookings()
                .assertStatus(200)
                .validateSchema("schema/get-bookings-schema.json")
                .as(GetAllBookingsResponse.class);

        Assert.assertTrue(getAllResponse.isSuccess());
        Assert.assertEquals(getAllResponse.getMessage(), AppConstants.GET_ALL_BOOKINGS_MESSAGE);

        Booking booking = getAllResponse.getData().stream()
                .filter(b -> b.getShow().getId().equals(showResponse.getShow().getId()))
                .findFirst()
                .orElse(null);

        Assert.assertNotNull(booking, "Newly created booking not found in getAllBookings response");

        Document mdb_Document = MongoHelper.findOne(mdb_BookingsCollection, "_id",booking.getId() );

        Assert.assertEquals(booking.getShow().getId(),    mdb_Document.get("show").toString());
        Assert.assertEquals(booking.getUser().getId(),    mdb_Document.get("user").toString());
        Assert.assertEquals(booking.getSeats(),           mdb_Document.get("seats"));
        Assert.assertEquals(booking.getTransactionId(),   mdb_Document.get("transactionId").toString());
        Assert.assertNotNull(booking.getId());
        Assert.assertNotNull(booking.getCreatedAt());
        Assert.assertNotNull(booking.getUpdatedAt());
    }
}