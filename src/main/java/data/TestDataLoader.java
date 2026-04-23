package data;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.model.Movie;
import api.model.Show;
import api.model.Theatre;
import api.model.UsersList;

public class TestDataLoader {
	
	public static UsersList loadUsers(String fileName){
		
		ObjectMapper mapper = new ObjectMapper();
		
		InputStream is = TestDataLoader.class
		     .getClassLoader()
		     .getResourceAsStream("testdata/" + fileName);
		
		try {
			return mapper.readValue(is, UsersList.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Show loadShows(String fileName){
		
		ObjectMapper mapper = new ObjectMapper();
		
		InputStream is = TestDataLoader.class
		     .getClassLoader()
		     .getResourceAsStream("testdata/" + fileName);
		
		try {
			return mapper.readValue(is, Show.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Movie loadMovies(String fileName){
		
		ObjectMapper mapper = new ObjectMapper();
		
		InputStream is = TestDataLoader.class
		     .getClassLoader()
		     .getResourceAsStream("testdata/" + fileName);
		
		try {
			return mapper.readValue(is, Movie.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}	
	
	public static Theatre loadTheatres(String fileName) {
		ObjectMapper mapper = new ObjectMapper();
		
		InputStream is = TestDataLoader.class
				.getClassLoader()
				.getResourceAsStream("testdata/" + fileName);
		
		try {
			return mapper.readValue(is, Theatre.class);
		} catch (StreamReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Show loadPaymentDetails(String fileName){
		
		ObjectMapper mapper = new ObjectMapper();
		
		InputStream is = TestDataLoader.class
		     .getClassLoader()
		     .getResourceAsStream("testdata/" + fileName);
		
		try {
			return mapper.readValue(is, Show.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}