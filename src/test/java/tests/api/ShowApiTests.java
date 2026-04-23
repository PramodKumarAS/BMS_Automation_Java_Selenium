package tests.api;

import java.time.Instant;
import java.util.Date;

import org.bson.Document;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mongodb.client.MongoCollection;

import api.builder.RequestBuilder;
import api.model.AddShowRequest;
import api.model.AddShowResponse;
import api.model.GetAllShowsByTheatreResponse;
import api.model.GetShowResponse;
import api.model.Show;
import api.model.ShowModel;
import api.model.UpdateShowResponse;
import base.APIBaseTest;
import constants.AppConstants;
import data.MongoConnection;
import data.MongoHelper;
import data.TestDataLoader;

public class ShowApiTests extends APIBaseTest {

    Show showData        = null;
    MongoCollection<Document> mdb_ShowsCollection   = null;
    MongoCollection<Document> mdb_MoviesCollection  = null;
    MongoCollection<Document> mdb_TheatresCollection = null;
    String movieId   = null;
    String theatreId = null;

    @BeforeClass
    public void setup() {
        showData        = TestDataLoader.loadShows("shows.json");

        mdb_ShowsCollection    = MongoConnection.connect(AppConstants.MONGO_DB_NAME, AppConstants.MONGO_SHOWS_COLLECTION);
        mdb_MoviesCollection   = MongoConnection.connect(AppConstants.MONGO_DB_NAME, AppConstants.MONGO_MOVIES_COLLECTION);
        mdb_TheatresCollection = MongoConnection.connect(AppConstants.MONGO_DB_NAME, AppConstants.MONGO_THEATRES_COLLECTION);

        Document mdb_Movie = MongoHelper.findOneByAnyParams(mdb_MoviesCollection, "movieName", AppConstants.SEED_MOVIE_NAME);
        movieId = mdb_Movie.getObjectId("_id").toHexString();

        Document mdb_Theatre = MongoHelper.findOneByAnyParams(mdb_TheatresCollection, "name", AppConstants.SEED_THEATRE_NAME);
        theatreId = mdb_Theatre.getObjectId("_id").toHexString();
    }

    @AfterMethod
    public void cleanup() {
        MongoHelper.deleteOne(mdb_ShowsCollection, "name", showData.getName());
    }

    @Test(groups = {"@smoke"},description = "POST /add-show: Should return 200 with success message and persist show data in MongoDB when valid show details are provided")
    public void shouldPersistShowInDB_whenValidShowIsAdded() {
        AddShowRequest request = RequestBuilder.buildAddShowRequest(
        		showData.getName(), 
        		showData.getDate(),
        		showData.getTime(), 
                movieId, 
                showData.getTicketPrice(),
                showData.getTotalSeats(),
                theatreId);

        AddShowResponse response = showClient.addShow(request)
                .assertStatus(200)
                .validateSchema("schema/add-show-schema.json")
                .as(AddShowResponse.class);

        Document mdb_Document = MongoHelper.findOne(mdb_ShowsCollection, "_id", response.getShow().getId());

        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals(response.getMessage(), AppConstants.SHOW_SUCCESS_MESSAGE);

        Assert.assertEquals(response.getShow().getName(),        mdb_Document.get("name").toString());
        Assert.assertEquals(response.getShow().getTime(),        mdb_Document.get("time").toString());
        Assert.assertEquals(response.getShow().getMovie(),       mdb_Document.get("movie").toString());
        Assert.assertEquals(response.getShow().getTheatre(),     mdb_Document.get("theatre").toString());
        Assert.assertEquals(response.getShow().getTicketPrice(), mdb_Document.get("ticketPrice"));
        Assert.assertEquals(response.getShow().getTotalSeats(),  mdb_Document.get("totalSeats"));
        Assert.assertEquals(Instant.parse(response.getShow().getDate()),((Date) mdb_Document.get("date")).toInstant());
        Assert.assertNotNull(response.getShow().getId());
        Assert.assertNotNull(response.getShow().getCreatedAt());
        Assert.assertNotNull(response.getShow().getUpdatedAt());
    }

    @Test(groups = {"@regression"},description = "PUT /update-show: Should return 200 and reflect updated fields in response and MongoDB when an existing show is modified")
    public void shouldReflectUpdatedFieldsInResponseAndDB_whenExistingShowIsModified() {

        AddShowRequest request = RequestBuilder.buildAddShowRequest(
        		showData.getName(), 
        		showData.getDate(),
        		showData.getTime(), 
                movieId, 
                showData.getTicketPrice(),
                showData.getTotalSeats(),
                theatreId);

        AddShowResponse response = showClient.addShow(request)
                .assertStatus(200)
                .as(AddShowResponse.class);

        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals(response.getMessage(), AppConstants.SHOW_SUCCESS_MESSAGE);

        int updatedTicketPrice = 500;
        AddShowRequest updateRequest = RequestBuilder.buildUpdateShowRequest(
        		response.getShow().getId(),
        		showData.getName(), 
        		showData.getDate(),
        		showData.getTime(), 
                movieId, 
                updatedTicketPrice,
                showData.getTotalSeats(),
                theatreId);

        UpdateShowResponse updateResponse = showClient.updateShow(updateRequest)
                .assertStatus(200)
                .validateSchema("schema/update-show-schema.json")
                .as(UpdateShowResponse.class);

        Document mdb_Document = MongoHelper.findOne(mdb_ShowsCollection, "_id", response.getShow().getId());

        Assert.assertTrue(updateResponse.isSuccess());
        Assert.assertEquals(updateResponse.getMessage(), AppConstants.SHOW_UPDATE_MESSAGE);

        Assert.assertEquals(updateResponse.getUpdatedShow().getName(),        mdb_Document.get("name").toString());
        Assert.assertEquals(updateResponse.getUpdatedShow().getTime(),        mdb_Document.get("time").toString());
        Assert.assertEquals(updateResponse.getUpdatedShow().getMovie(),       mdb_Document.get("movie").toString());
        Assert.assertEquals(updateResponse.getUpdatedShow().getTheatre(),     mdb_Document.get("theatre").toString());
        Assert.assertEquals(updateResponse.getUpdatedShow().getTicketPrice(), mdb_Document.get("ticketPrice"));
        Assert.assertEquals(updateResponse.getUpdatedShow().getTotalSeats(),  mdb_Document.get("totalSeats"));
        Assert.assertEquals(Instant.parse(updateResponse.getUpdatedShow().getDate()),((Date) mdb_Document.get("date")).toInstant());
        Assert.assertNotNull(updateResponse.getUpdatedShow().getId());
        Assert.assertNotNull(updateResponse.getUpdatedShow().getCreatedAt());
        Assert.assertNotNull(updateResponse.getUpdatedShow().getUpdatedAt());
    }

    @Test(groups = {"@regression"},description = "DELETE /delete-show: Should return 200 and remove the show document from MongoDB when a valid show ID is provided")
    public void shouldRemoveShowFromDB_whenValidShowIdIsDeleted() {

        AddShowRequest request = RequestBuilder.buildAddShowRequest(
        		showData.getName(), 
        		showData.getDate(),
        		showData.getTime(), 
                movieId, 
                showData.getTicketPrice(),
                showData.getTotalSeats(),
                theatreId);

        AddShowResponse response = showClient.addShow(request)
                .assertStatus(200)
                .as(AddShowResponse.class);

        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals(response.getMessage(), AppConstants.SHOW_SUCCESS_MESSAGE);

        showClient.deleteShow(response.getShow().getId())
                .assertStatus(200)
                .validateSchema("schema/delete-show-schema.json");

        Document mdb_Document = MongoHelper.findOne(mdb_ShowsCollection, "_id", response.getShow().getId());
        Assert.assertNull(mdb_Document);
    }

    @Test(groups = {"@regression"},description = "GET /show/:showId: Should return 200 and return show details matching MongoDB document when fetched by valid show ID")
    public void shouldReturnShowDetails_whenFetchedByValidShowId() {

        AddShowRequest request = RequestBuilder.buildAddShowRequest(
        		showData.getName(), 
        		showData.getDate(),
        		showData.getTime(), 
                movieId, 
                showData.getTicketPrice(),
                showData.getTotalSeats(),
                theatreId);

        AddShowResponse response = showClient.addShow(request)
                .assertStatus(200)
                .as(AddShowResponse.class);

        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals(response.getMessage(), AppConstants.SHOW_SUCCESS_MESSAGE);

        showClient.getAllShowsByTheatre(theatreId).assertStatus(200);

        GetShowResponse getByIdResponse = showClient.getShowById(response.getShow().getId())
                .assertStatus(200)
                .validateSchema("schema/get-show-by-id-schema.json")
                .as(GetShowResponse.class);

        Document mdb_Document = MongoHelper.findOne(mdb_ShowsCollection, "_id", response.getShow().getId());

        Assert.assertEquals(getByIdResponse.getData().getName(),        mdb_Document.get("name").toString());
        Assert.assertEquals(getByIdResponse.getData().getTime(),        mdb_Document.get("time").toString());
        Assert.assertEquals(getByIdResponse.getData().getTicketPrice(), mdb_Document.get("ticketPrice"));
        Assert.assertEquals(getByIdResponse.getData().getTotalSeats(),  mdb_Document.get("totalSeats"));       
        Assert.assertEquals(getByIdResponse.getData().getMovie().getId(),  movieId);        
        Assert.assertEquals(getByIdResponse.getData().getTheatre().getId(),  theatreId);        
        Assert.assertEquals(
                Instant.parse(getByIdResponse.getData().getDate()).toString().split("T")[0],
                Instant.now().toString().split("T")[0]);  
        Assert.assertNotNull(getByIdResponse.getData().getId());
        Assert.assertNotNull(getByIdResponse.getData().getCreatedAt());
        Assert.assertNotNull(getByIdResponse.getData().getUpdatedAt());
    }

    @Test(groups = {"@regression"},description = "GET /shows/theatre/:theatreId: Should return 200 and include the newly added show in the response list, with fields matching MongoDB document")
    public void shouldReturnShowInList_whenFetchingAllShowsByTheatre() {

        AddShowRequest request = RequestBuilder.buildAddShowRequest(
        		showData.getName(), 
        		showData.getDate(),
        		showData.getTime(), 
                movieId, 
                showData.getTicketPrice(),
                showData.getTotalSeats(),
                theatreId);

        AddShowResponse response = showClient.addShow(request)
                .assertStatus(200)
                .as(AddShowResponse.class);

        Assert.assertTrue(response.isSuccess());
        Assert.assertEquals(response.getMessage(), AppConstants.SHOW_SUCCESS_MESSAGE);

        GetAllShowsByTheatreResponse getAllResponse = showClient.getAllShowsByTheatre(theatreId)
                .assertStatus(200)
                 .validateSchema("schema/get-shows-schema.json")
                .as(GetAllShowsByTheatreResponse.class);

        Assert.assertTrue(getAllResponse.isSuccess());
        Assert.assertEquals(getAllResponse.getMessage(), AppConstants.GET_ALL_SHOWS_MESSAGE);

        ShowModel getByIdResponse = getAllResponse.getShows().stream()
                .filter(s -> s.getId().equals(response.getShow().getId()))
                .findFirst()
                .orElse(null);

        Assert.assertNotNull(getByIdResponse, "Newly added show not found in getAllShowsByTheatre response");

        Document mdb_Document = MongoHelper.findOne(mdb_ShowsCollection, "_id", response.getShow().getId());

        Assert.assertEquals(getByIdResponse.getName(),        mdb_Document.get("name").toString());
        Assert.assertEquals(getByIdResponse.getTime(),        mdb_Document.get("time").toString());
        Assert.assertEquals(getByIdResponse.getTicketPrice(), mdb_Document.get("ticketPrice"));
        Assert.assertEquals(getByIdResponse.getTotalSeats(),  mdb_Document.get("totalSeats"));       
        Assert.assertEquals(getByIdResponse.getMovie().getId(),  movieId);        
        Assert.assertEquals(getByIdResponse.getTheatre(),  theatreId);        
        Assert.assertEquals(getByIdResponse.getDate(),Instant.now().toString().split("T")[0]);  
        Assert.assertNotNull(getByIdResponse.getId());
        Assert.assertNotNull(getByIdResponse.getCreatedAt());
        Assert.assertNotNull(getByIdResponse.getUpdatedAt());
    }
}