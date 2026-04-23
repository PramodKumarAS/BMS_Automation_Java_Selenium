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
import api.model.AddMovieRequest;
import api.model.AddMovieResponse;
import api.model.DeleteTheatreResponse;
import api.model.GetMoviesResponse;
import api.model.Movie;
import api.model.UpdateMovieResponse;
import base.APIBaseTest;
import constants.AppConstants;
import data.MongoConnection;
import data.MongoHelper;
import data.TestDataLoader;

public class MovieApiTests extends APIBaseTest {
	Movie movieData = null;
	MongoCollection<Document> mdb_TestCollection =null;
	
	@BeforeClass
	public void setup() {
		movieData = TestDataLoader.loadMovies("movie.json");
		mdb_TestCollection = MongoConnection.connect(AppConstants.MONGO_DB_NAME, AppConstants.MONGO_MOVIES_COLLECTION);

	}
	
	@AfterMethod
	public void beforeTest() {
		MongoHelper.deleteOne(mdb_TestCollection, "movieName",movieData.getMovieName());
	}

	@Test(groups = {"@smoke"}, description = "POST /add-movie: Should return 200 with success message and persist movie data in MongoDB when valid partner adds a new movie")
	public void shouldPersistMovieInDB_whenValidAdminAddsNewMovie() {			
		AddMovieRequest request = RequestBuilder.
				buildAddMovieRequest(movieData.getMovieName(), movieData.getDescription(), 
						movieData.getDuration(), movieData.getLanguage(),movieData.getReleaseDate(),movieData.getGenre(),movieData.getPoster());
		
		AddMovieResponse response = movieClient.addMovie(request)
		 			 .assertStatus(200)
		 			 .validateSchema("schema/add-movie-schema.json")
		 			 .as(AddMovieResponse.class);
				
		Document mdb_Document = MongoHelper.findOne(mdb_TestCollection, "_id", response.getMovie().getId());
		
		Assert.assertEquals(response.isSuccess(), true);
		Assert.assertEquals(response.getMessage(), AppConstants.MOVIE_SUCCESS_MESSAGE);
		
		Assert.assertEquals(response.getMovie().getMovieName(), mdb_Document.get("movieName").toString());
		Assert.assertEquals(response.getMovie().getDescription(), mdb_Document.get("description").toString());
		Assert.assertEquals(response.getMovie().getDuration(),mdb_Document.get("duration"));
		Assert.assertEquals(response.getMovie().getGenre(), mdb_Document.get("genre").toString());
		Assert.assertEquals(response.getMovie().getLanguage(), mdb_Document.get("language").toString());
		Assert.assertEquals(Instant.parse(response.getMovie().getReleaseDate()),((Date) mdb_Document.get("releaseDate")).toInstant());		
		Assert.assertEquals(response.getMovie().getPoster(),mdb_Document.get("poster"));
		Assert.assertNotNull(response.getMovie().getId());
	}
	
	@Test(groups = {"@regression"}, description = "PUT /update-movie: Should return 200 and reflect updated fields in response and MongoDB when an existing movie is modified")
	public void shouldReflectUpdatedFieldsInResponseAndDB_whenExistingMovieIsModified() {
		
		AddMovieRequest request = RequestBuilder.
				buildAddMovieRequest(movieData.getMovieName(), movieData.getDescription(), 
						movieData.getDuration(), movieData.getLanguage(),movieData.getReleaseDate(),movieData.getGenre(),movieData.getPoster());
		
		AddMovieResponse response = movieClient.addMovie(request)
		 			 .assertStatus(200)
		 			 .validateSchema("schema/add-movie-schema.json")
		 			 .as(AddMovieResponse.class);
					
		Assert.assertEquals(response.isSuccess(), true);
		Assert.assertEquals(response.getMessage(), AppConstants.MOVIE_SUCCESS_MESSAGE);
		
		String updatedDescription = movieData.getDescription() + " Updated Description";
		AddMovieRequest updateRequest = RequestBuilder.
				buildUpdateMovieRequest(response.getMovie().getId(),movieData.getMovieName(),updatedDescription, 
						movieData.getDuration(), movieData.getLanguage(),movieData.getReleaseDate(),movieData.getGenre(),movieData.getPoster());
		
		UpdateMovieResponse updateMovieResponse = movieClient.updateMovie(updateRequest)
					.assertStatus(200)
					.validateSchema("schema/update-movie-schema.json")
					.as(UpdateMovieResponse.class);
		
		Document mdb_Document = MongoHelper.findOne(mdb_TestCollection, "_id", response.getMovie().getId());
		Assert.assertEquals(updateMovieResponse.getUpdatedMovie().getMovieName(), mdb_Document.get("movieName").toString());
		Assert.assertEquals(updateMovieResponse.getUpdatedMovie().getDescription(), mdb_Document.get("description").toString());
		Assert.assertEquals(updateMovieResponse.getUpdatedMovie().getDuration(),mdb_Document.get("duration"));
		Assert.assertEquals(updateMovieResponse.getUpdatedMovie().getGenre(), mdb_Document.get("genre").toString());
		Assert.assertEquals(updateMovieResponse.getUpdatedMovie().getLanguage(), mdb_Document.get("language").toString());
		Assert.assertEquals(Instant.parse(updateMovieResponse.getUpdatedMovie().getReleaseDate()),((Date) mdb_Document.get("releaseDate")).toInstant());		
		Assert.assertEquals(updateMovieResponse.getUpdatedMovie().getPoster(),mdb_Document.get("poster"));
		Assert.assertNotNull(updateMovieResponse.getUpdatedMovie().getId());
	}	

	@Test(groups = {"@regression"}, description = "DELETE /delete-movie: Should return 200 and remove the movie document from MongoDB when a valid movie ID is provided")
	public void shouldRemoveTheatreFromDB_whenValidMovieIdIsDeleted() {
		
		AddMovieRequest request = RequestBuilder.
				buildAddMovieRequest(movieData.getMovieName(), movieData.getDescription(), 
						movieData.getDuration(), movieData.getLanguage(),movieData.getReleaseDate(),movieData.getGenre(),movieData.getPoster());
		
		AddMovieResponse response = movieClient.addMovie(request)
		 			 .assertStatus(200)
		 			 .validateSchema("schema/add-movie-schema.json")
		 			 .as(AddMovieResponse.class);
					
		Assert.assertEquals(response.isSuccess(), true);
		Assert.assertEquals(response.getMessage(), AppConstants.MOVIE_SUCCESS_MESSAGE);
				
		movieClient.deleteMovie(response.getMovie().getId())
					.assertStatus(200)
					.validateSchema("schema/delete-movie-schema.json")
					.as(DeleteTheatreResponse.class);
		
		Document mdb_Document = MongoHelper.findOne(mdb_TestCollection, "_id", response.getMovie().getId());
		Assert.assertNull(mdb_Document);	 			 	
	}		
	
	@Test(groups = {"@regression"}, description = "GET /{movieId}: Should return 200 and include the newly added movie in the response list, with fields matching MongoDB document")
	public void shouldReturnMovieInMoviesList_whenFilteredByMovieId() {
		
		AddMovieRequest request = RequestBuilder.
				buildAddMovieRequest(movieData.getMovieName(), movieData.getDescription(), 
						movieData.getDuration(), movieData.getLanguage(),movieData.getReleaseDate(),movieData.getGenre(),movieData.getPoster());
		
		AddMovieResponse response = movieClient.addMovie(request)
		 			 .assertStatus(200)
		 			 .validateSchema("schema/add-movie-schema.json")
		 			 .as(AddMovieResponse.class);
					
		Assert.assertEquals(response.isSuccess(), true);
		Assert.assertEquals(response.getMessage(), AppConstants.MOVIE_SUCCESS_MESSAGE);
				
		AddMovieResponse getMovieByIdResponse = movieClient.getMovieById(response.getMovie().getId())
					.assertStatus(200)
					.validateSchema("schema/add-movie-schema.json")
					.as(AddMovieResponse.class);
		
		Assert.assertEquals(getMovieByIdResponse.isSuccess(), true);
		Assert.assertEquals(getMovieByIdResponse.getMessage(), AppConstants.GET_MOVIE_BY_ID_SUCCESS_MESSAGE);
				
		Document mdb_Document = MongoHelper.findOne(mdb_TestCollection, "_id", response.getMovie().getId());
				
		Assert.assertEquals(getMovieByIdResponse.getMovie().getMovieName(), mdb_Document.get("movieName").toString());
		Assert.assertEquals(getMovieByIdResponse.getMovie().getDescription(), mdb_Document.get("description").toString());
		Assert.assertEquals(getMovieByIdResponse.getMovie().getDuration(),mdb_Document.get("duration"));
		Assert.assertEquals(getMovieByIdResponse.getMovie().getGenre(), mdb_Document.get("genre").toString());
		Assert.assertEquals(getMovieByIdResponse.getMovie().getLanguage(), mdb_Document.get("language").toString());
		Assert.assertEquals(Instant.parse(getMovieByIdResponse.getMovie().getReleaseDate()),((Date) mdb_Document.get("releaseDate")).toInstant());		
		Assert.assertEquals(getMovieByIdResponse.getMovie().getPoster(),mdb_Document.get("poster"));
		Assert.assertNotNull(getMovieByIdResponse.getMovie().getId());
	}	
			
	@Test(groups = {"@regression"}, description = "GET /movies: Should return 200 and include the newly added movie in the global list, with fields matching MongoDB document")
	public void shouldReturnMovieInGlobalList_whenFetchingAllMovies() {
		
		AddMovieRequest request = RequestBuilder.
				buildAddMovieRequest(movieData.getMovieName(), movieData.getDescription(), 
						movieData.getDuration(), movieData.getLanguage(),movieData.getReleaseDate(),movieData.getGenre(),movieData.getPoster());
		
		AddMovieResponse response = movieClient.addMovie(request)
		 			 .assertStatus(200)
		 			 .validateSchema("schema/add-movie-schema.json")
		 			 .as(AddMovieResponse.class);
					
		Assert.assertEquals(response.isSuccess(), true);
		Assert.assertEquals(response.getMessage(), AppConstants.MOVIE_SUCCESS_MESSAGE);
				
		GetMoviesResponse getAllMoviesResponse = movieClient.getAllMovies()
					.assertStatus(200)
					.validateSchema("schema/getAllMovies-schema.json")
					.as(GetMoviesResponse.class);
		
		Assert.assertEquals(getAllMoviesResponse.isSuccess(), true);
		Assert.assertEquals(getAllMoviesResponse.getMessage(), AppConstants.GET_MOVIE_BY_ID_SUCCESS_MESSAGE);
		
		Movie addedMovieResponse = getAllMoviesResponse.getMovies().stream()
				.filter(t->t.getId().equals(response.getMovie().getId()))
				.findFirst()
		        .orElse(null);				
		
		Document mdb_Document = MongoHelper.findOne(mdb_TestCollection, "_id", response.getMovie().getId());
				
		Assert.assertEquals(addedMovieResponse.getMovieName(), mdb_Document.get("movieName").toString());
		Assert.assertEquals(addedMovieResponse.getDescription(), mdb_Document.get("description").toString());
		Assert.assertEquals(addedMovieResponse.getDuration(),mdb_Document.get("duration"));
		Assert.assertEquals(addedMovieResponse.getGenre(), mdb_Document.get("genre").toString());
		Assert.assertEquals(addedMovieResponse.getLanguage(), mdb_Document.get("language").toString());
		Assert.assertEquals(Instant.parse(addedMovieResponse.getReleaseDate()),((Date) mdb_Document.get("releaseDate")).toInstant());		
		Assert.assertEquals(addedMovieResponse.getPoster(),mdb_Document.get("poster"));
		Assert.assertNotNull(addedMovieResponse.getId());	 			 				
	}
}