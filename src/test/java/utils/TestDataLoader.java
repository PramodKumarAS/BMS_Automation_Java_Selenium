package utils;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

import api.model.ShowModel;
import api.model.UsersList;

public class TestDataLoader {
	
	public static UsersList loadUsers(String fileName) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		InputStream is = TestDataLoader.class
		     .getClassLoader()
		     .getResourceAsStream("testdata/" + fileName);
		
		return mapper.readValue(is, UsersList.class);
	}
	
	public static ShowModel loadShows(String fileName) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		InputStream is = TestDataLoader.class
		     .getClassLoader()
		     .getResourceAsStream("testdata/" + fileName);
		
		return mapper.readValue(is, ShowModel.class);
	}
	
}