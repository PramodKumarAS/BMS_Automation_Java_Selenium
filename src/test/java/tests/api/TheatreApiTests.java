package tests.api;

import org.bson.Document;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mongodb.client.MongoCollection;

import api.builder.RequestBuilder;
import api.model.AddTheatreRequest;
import api.model.AddTheatreResponse;
import api.model.CurrentUserResponse;
import api.model.DeleteTheatreResponse;
import api.model.GetTheatresResponse;
import api.model.Theatre;
import api.model.UpdateTheatreResponse;
import base.APIBaseTest;
import constants.AppConstants;
import data.MongoConnection;
import data.MongoHelper;
import data.TestDataLoader;

public class TheatreApiTests extends APIBaseTest {
	
	Theatre theatreData = null;
	MongoCollection<Document> mdb_TestCollection =null;
	
	@BeforeClass
	public void setup() {
		theatreData = TestDataLoader.loadTheatres("theatre.json");
		mdb_TestCollection = MongoConnection.connect(AppConstants.MONGO_DB_NAME, AppConstants.MONGO_THEATRES_COLLECTION);

	}
	
	@AfterMethod
	public void beforeTest() {
		MongoHelper.deleteOne(mdb_TestCollection, "name",theatreData.getName());
	}

	@Test(groups = {"@smoke"}, description = "POST /add-theatre: Should return 200 with success message and persist theatre data in MongoDB when valid partner adds a new theatre")
	public void shouldPersistTheatreInDB_whenValidPartnerAddsNewTheatre() {
		
		CurrentUserResponse currUserResposne = authClient.getCurrentUser(AppConstants.ROLE_PARTNER)
					.assertStatus(200)
					.as(CurrentUserResponse.class);
	
		AddTheatreRequest request = RequestBuilder.
				buildAddTheatreRequest(theatreData.getName(), theatreData.getAddress(), 
						theatreData.getEmail(), theatreData.getPhone(),currUserResposne.getUser().getId());
		
		AddTheatreResponse response = theatreClient.addTheatre(request)
		 			 .assertStatus(200)
		 			 .validateSchema("schema/add-theatre-schema.json")
		 			 .as(AddTheatreResponse.class);
				
		Document mdb_Document = MongoHelper.findOne(mdb_TestCollection, "_id", response.getTheatre().getId());
		
		Assert.assertEquals(response.isSuccess(), true);
		Assert.assertEquals(response.getMessage(), AppConstants.THEATRE_SUCCESS_MESSAGE);
		
		Assert.assertEquals(response.getTheatre().getName(), mdb_Document.get("name").toString());
		Assert.assertEquals(response.getTheatre().getAddress(), mdb_Document.get("address").toString());
		Assert.assertEquals(response.getTheatre().getEmail(), mdb_Document.get("email").toString());
		Assert.assertEquals(response.getTheatre().getPhone(),String.valueOf(((Number) mdb_Document.get("phone")).longValue()));
		Assert.assertEquals(response.getTheatre().getOwner(), mdb_Document.get("owner").toString());
		Assert.assertEquals(response.getTheatre().isActive(), mdb_Document.get("isActive"));
		Assert.assertNotNull(response.getTheatre().getId());
		Assert.assertNotNull(response.getTheatre().getCreatedAt());
		Assert.assertNotNull(response.getTheatre().getUpdatedAt());		 			 	
	}
	
	@Test(groups = {"@regression"}, description = "PUT /update-theatre: Should return 200 and reflect updated address in response and MongoDB when an existing theatre is modified by its owner")
	public void shouldReflectUpdatedFieldsInResponseAndDB_whenExistingTheatreIsModified() {
		
		CurrentUserResponse currUserResposne = authClient.getCurrentUser(AppConstants.ROLE_PARTNER)
					.assertStatus(200)
					.as(CurrentUserResponse.class);
	
		AddTheatreRequest request = RequestBuilder.
				buildAddTheatreRequest(theatreData.getName(), theatreData.getAddress(), 
						theatreData.getEmail(), theatreData.getPhone(),currUserResposne.getUser().getId());
		
		AddTheatreResponse response = theatreClient.addTheatre(request)
		 			 .assertStatus(200)
		 			 .as(AddTheatreResponse.class);
					
		Assert.assertEquals(response.isSuccess(), true);
		Assert.assertEquals(response.getMessage(), AppConstants.THEATRE_SUCCESS_MESSAGE);
		
		String updatedAddress = theatreData.getAddress() + " Updated Address";
		AddTheatreRequest updateRequest = RequestBuilder.
				buildUpdateTheatreRequest(response.getTheatre().getId(),theatreData.getName(), updatedAddress, 
						theatreData.getEmail(), theatreData.getPhone(),currUserResposne.getUser().getId());
		
		UpdateTheatreResponse updateTheatreResponse = theatreClient.updateTheatre(updateRequest)
					.assertStatus(200)
					.validateSchema("schema/update-theatre-schema.json")
					.as(UpdateTheatreResponse.class);
		
		Document mdb_Document = MongoHelper.findOne(mdb_TestCollection, "_id", response.getTheatre().getId());
		Assert.assertEquals(updateTheatreResponse.getUpdatedTheatre().getName(), mdb_Document.get("name").toString());
		Assert.assertEquals(updateTheatreResponse.getUpdatedTheatre().getAddress(), mdb_Document.get("address").toString());
		Assert.assertEquals(updateTheatreResponse.getUpdatedTheatre().getEmail(), mdb_Document.get("email").toString());
		Assert.assertEquals(updateTheatreResponse.getUpdatedTheatre().getPhone(),String.valueOf(((Number) mdb_Document.get("phone")).longValue()));
		Assert.assertEquals(updateTheatreResponse.getUpdatedTheatre().getOwner(), mdb_Document.get("owner").toString());
		Assert.assertEquals(updateTheatreResponse.getUpdatedTheatre().isActive(), mdb_Document.get("isActive"));
		Assert.assertNotNull(updateTheatreResponse.getUpdatedTheatre().getId());
		Assert.assertNotNull(updateTheatreResponse.getUpdatedTheatre().getCreatedAt());
		Assert.assertNotNull(updateTheatreResponse.getUpdatedTheatre().getUpdatedAt());		 			 	
	}	
	
	@Test(groups = {"@regression"}, description = "DELETE /delete-theatre: Should return 200 and remove the theatre document from MongoDB when a valid theatre ID is provided")
	public void shouldRemoveTheatreFromDB_whenValidTheatreIdIsDeleted() {
		
		CurrentUserResponse currUserResposne = authClient.getCurrentUser(AppConstants.ROLE_PARTNER)
					.assertStatus(200)
					.as(CurrentUserResponse.class);
	
		AddTheatreRequest request = RequestBuilder.
				buildAddTheatreRequest(theatreData.getName(), theatreData.getAddress(), 
						theatreData.getEmail(), theatreData.getPhone(),currUserResposne.getUser().getId());
		
		AddTheatreResponse response = theatreClient.addTheatre(request)
		 			 .assertStatus(200)
		 			 .as(AddTheatreResponse.class);
					
		Assert.assertEquals(response.isSuccess(), true);
		Assert.assertEquals(response.getMessage(), AppConstants.THEATRE_SUCCESS_MESSAGE);
				
		theatreClient.deleteTheatre(response.getTheatre().getId())
					.assertStatus(200)
					.validateSchema("schema/delete-theatre-schema.json")
					.as(DeleteTheatreResponse.class);
		
		Document mdb_Document = MongoHelper.findOne(mdb_TestCollection, "_id", response.getTheatre().getId());
		Assert.assertNull(mdb_Document);	 			 	
	}	
		
	@Test(groups = {"@regression"}, description = "GET /theatres/owner/{ownerId}: Should return 200 and include the newly added theatre in the response list, with fields matching MongoDB document")
	public void shouldReturnTheatreInOwnerList_whenFilteredByOwnerId() {
		
		CurrentUserResponse currUserResposne = authClient.getCurrentUser(AppConstants.ROLE_PARTNER)
					.assertStatus(200)
					.as(CurrentUserResponse.class);
	
		AddTheatreRequest request = RequestBuilder.
				buildAddTheatreRequest(theatreData.getName(), theatreData.getAddress(), 
						theatreData.getEmail(), theatreData.getPhone(),currUserResposne.getUser().getId());
		
		AddTheatreResponse response = theatreClient.addTheatre(request)
		 			 .assertStatus(200)
		 			 .as(AddTheatreResponse.class);
					
		Assert.assertEquals(response.isSuccess(), true);
		Assert.assertEquals(response.getMessage(), AppConstants.THEATRE_SUCCESS_MESSAGE);
				
		GetTheatresResponse getAllTheatreResponse = theatreClient.getTheatresByOwnerId(currUserResposne.getUser().getId())
					.assertStatus(200)
					.validateSchema("schema/getAllTheatreByOwner-schema.json")
					.as(GetTheatresResponse.class);
		
		Assert.assertEquals(getAllTheatreResponse.isSuccess(), true);
		Assert.assertEquals(getAllTheatreResponse.getMessage(), AppConstants.GET_ALL_THEATRES_BY_OWNER_SUCCESS_MESSAGE);
		
//		Theatre addedTheatreResponse = null;
//
//		for(Theatre theatre: getAllTheatreResponse.getAllTheatres()) {
//			if(theatre.getId().equals(response.getTheatre().getId())) {
//				addedTheatreResponse =theatre;
//				return;
//			}
//		}
		
		Theatre addedTheatreResponse = getAllTheatreResponse.getAllTheatres().stream()
				.filter(t->t.getId().equals(response.getTheatre().getId()))
				.findFirst()
		        .orElse(null);				
		
		Document mdb_Document = MongoHelper.findOne(mdb_TestCollection, "_id", response.getTheatre().getId());
				
		Assert.assertEquals(addedTheatreResponse.getName(), mdb_Document.get("name").toString());
		Assert.assertEquals(addedTheatreResponse.getAddress(), mdb_Document.get("address").toString());
		Assert.assertEquals(addedTheatreResponse.getEmail(), mdb_Document.get("email").toString());
		Assert.assertEquals(addedTheatreResponse.getPhone(),String.valueOf(((Number) mdb_Document.get("phone")).longValue()));
		Assert.assertEquals(addedTheatreResponse.getOwner().get_id(), mdb_Document.get("owner").toString());
		Assert.assertEquals(addedTheatreResponse.isActive(), mdb_Document.get("isActive"));
		Assert.assertNotNull(addedTheatreResponse.getId());
		Assert.assertNotNull(addedTheatreResponse.getCreatedAt());
		Assert.assertNotNull(addedTheatreResponse.getUpdatedAt());		 			 				
	}	
		
	@Test(groups = {"@regression"}, description = "GET /theatres: Should return 200 and include the newly added theatre in the global list, with fields matching MongoDB document")
	public void shouldReturnTheatreInGlobalList_whenFetchingAllTheatres() {
		
		CurrentUserResponse currUserResposne = authClient.getCurrentUser(AppConstants.ROLE_PARTNER)
					.assertStatus(200)
					.as(CurrentUserResponse.class);
	
		AddTheatreRequest request = RequestBuilder.
				buildAddTheatreRequest(theatreData.getName(), theatreData.getAddress(), 
						theatreData.getEmail(), theatreData.getPhone(),currUserResposne.getUser().getId());
		
		AddTheatreResponse response = theatreClient.addTheatre(request)
		 			 .assertStatus(200)
		 			 .as(AddTheatreResponse.class);
					
		Assert.assertEquals(response.isSuccess(), true);
		Assert.assertEquals(response.getMessage(), AppConstants.THEATRE_SUCCESS_MESSAGE);
				
		GetTheatresResponse getAllTheatreResponse = theatreClient.getAllTheatres()
					.assertStatus(200)
					.validateSchema("schema/getAllTheatres-schema.json")
					.as(GetTheatresResponse.class);
		
		Assert.assertEquals(getAllTheatreResponse.isSuccess(), true);
		Assert.assertEquals(getAllTheatreResponse.getMessage(), AppConstants.GET_ALL_THEATRES_SUCCESS_MESSAGE);
		
		Theatre addedTheatreResponse = getAllTheatreResponse.getAllTheatres().stream()
				.filter(t->t.getId().equals(response.getTheatre().getId()))
				.findFirst()
		        .orElse(null);				
		
		Document mdb_Document = MongoHelper.findOne(mdb_TestCollection, "_id", response.getTheatre().getId());
				
		Assert.assertEquals(addedTheatreResponse.getName(), mdb_Document.get("name").toString());
		Assert.assertEquals(addedTheatreResponse.getAddress(), mdb_Document.get("address").toString());
		Assert.assertEquals(addedTheatreResponse.getEmail(), mdb_Document.get("email").toString());
		Assert.assertEquals(addedTheatreResponse.getPhone(),String.valueOf(((Number) mdb_Document.get("phone")).longValue()));
		Assert.assertEquals(addedTheatreResponse.getOwner().get_id(), mdb_Document.get("owner").toString());
		Assert.assertEquals(addedTheatreResponse.isActive(), mdb_Document.get("isActive"));
		Assert.assertNotNull(addedTheatreResponse.getId());
		Assert.assertNotNull(addedTheatreResponse.getCreatedAt());
		Assert.assertNotNull(addedTheatreResponse.getUpdatedAt());		 			 				
	}
}