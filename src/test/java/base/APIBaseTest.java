package base;

import java.io.IOException;

import org.testng.annotations.BeforeClass;

import api.client.AuthClient;
import api.model.User;
import api.model.UsersList;
import driver.TestDataLoader;
import utilities.RestAssuredConfig;

public class APIBaseTest {
	
	protected AuthClient authClient = new AuthClient();
	public User user =null;
	
	@BeforeClass
	public void setUp() {
		RestAssuredConfig.setUp();
		
		try {
			UsersList users = TestDataLoader.loadUsers("users.json");
			user = users.getUsers().get(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}