package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestDataLoader {
	
	public static String load(String fileName) throws IOException {
		
		byte[] fileData=Files.readAllBytes(Paths.get("src/test/resources/testdata/" + fileName));

		return new String(fileData);
	}
	
}