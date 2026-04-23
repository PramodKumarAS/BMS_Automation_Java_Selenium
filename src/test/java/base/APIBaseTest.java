package base;


import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import api.client.RestAssuredClient;
import api.endpoints.AuthClient;
import api.endpoints.BookClient;
import api.endpoints.MovieClient;
import api.endpoints.ShowClient;
import api.endpoints.TheatreClient;
import api.model.User;
import api.model.UsersList;
import data.MongoConnection;
import data.TestDataLoader;

public class APIBaseTest {
	
	protected AuthClient authClient = new AuthClient();
	protected TheatreClient theatreClient = new TheatreClient();
	protected MovieClient movieClient = new MovieClient();
	protected ShowClient showClient = new ShowClient();
	protected BookClient bookClient = new BookClient();

	public User user =null;
	
	@BeforeClass
	public void setUp() {
		RestAssuredClient.setUp();
		
		UsersList users = TestDataLoader.loadUsers("users.json");
		user = users.getUsers().get(0);				
	}
	
	@AfterClass
	public void tearDown() {
	    MongoConnection.close();
	}
}